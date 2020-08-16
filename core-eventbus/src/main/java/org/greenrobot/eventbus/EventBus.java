/*
 * Copyright (C) 2012-2016 Markus Junginger, greenrobot (http://greenrobot.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.greenrobot.eventbus;


import androidx.core.util.Pair;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;

/**
 * EventBus is a central publish/subscribe event system for Android. Events are posted ({@link #post(Object, String)}) to the
 * bus, which delivers it to subscribers that have a matching handler method for the event type. To receive events,
 * subscribers must register themselves to the bus using {@link #register(Object)}. Once registered, subscribers
 * receive events until {@link #unregister(Object)} is called. Event handling methods must be annotated by
 * {@link Subscribe}, must be public, return nothing (void), and have exactly one parameter
 * (the event).
 *
 * @author Markus Junginger, greenrobot
 */
public class EventBus {

    /**
     * Log tag, apps may override it.
     */
    public static String TAG = "EventBus";

    static volatile EventBus defaultInstance;
    /**
     * TODO 单例
     */
    private static final EventBusBuilder DEFAULT_BUILDER = EventBusBuilder.getDefault();
    private static final Map<Class<?>, List<Class<?>>> EVENT_TYPES_CACHE = new HashMap<>();

    /**
     * Map对象的值就是用来缓存订阅方法的信息的
     * key为EventType的class对象，value为Subscription对象的集合
     */
    private final Map<Class<?>, CopyOnWriteArrayList<Pair<String[], Subscription>>> subscriptionsByEventType;
    private final Map<Object, List<Class<?>>> typesBySubscriber;
    /**
     * 粘性操作
     * Key:发送接收的数据类型
     * Value:发送接收操作的tag和实际数据
     */
    private final Map<Class<?>, Pair<String, Object>> stickyEvents;

    /**
     * 多个线程操作同一个结果,但是互相之间不影响
     */
    private final ThreadLocal<PostingThreadState> currentPostingThreadState = new ThreadLocal<PostingThreadState>() {
        @Override
        protected PostingThreadState initialValue() {
            return new PostingThreadState();
        }
    };

    private final MainThreadSupport mainThreadSupport;
    private final Poster mainThreadPoster;
    private final BackgroundPoster backgroundPoster;
    private final AsyncPoster asyncPoster;
    private final SubscriberMethodFinder subscriberMethodFinder;
    private final ExecutorService executorService;

    private final boolean throwSubscriberException;
    private final boolean logSubscriberExceptions;
    private final boolean logNoSubscriberMessages;
    private final boolean sendSubscriberExceptionEvent;
    private final boolean sendNoSubscriberEvent;
    private final boolean eventInheritance;

    private final int indexCount;
    private final EventBusLogger logger;

    /**
     * Convenience singleton for apps using a process-wide EventBus instance.
     */
    public static EventBus getDefault() {
        //使用双重否定的单例模式
        EventBus instance = defaultInstance;
        if (instance == null) {
            synchronized (EventBus.class) {
                instance = EventBus.defaultInstance;
                if (instance == null) {
                    instance = EventBus.defaultInstance = new EventBus();
                }
            }
        }
        return instance;
    }

    /**
     * TODO 单例
     *
     * @return
     */
    public static EventBusBuilder builder() {
        return EventBusBuilder.getDefault();
    }

    /**
     * For unit test primarily.
     */
    public static void clearCaches() {
        SubscriberMethodFinder.clearCaches();
        EVENT_TYPES_CACHE.clear();
    }

    /**
     * Creates a new EventBus instance; each instance is a separate scope in which events are delivered. To use a
     * central bus, consider {@link #getDefault()}.
     */
    public EventBus() {
        this(DEFAULT_BUILDER);
    }

    EventBus(EventBusBuilder builder) {
        logger = builder.getLogger();
        subscriptionsByEventType = new HashMap<>();
        typesBySubscriber = new HashMap<>();
        stickyEvents = new ConcurrentHashMap<>();
        mainThreadSupport = builder.getMainThreadSupport();
        mainThreadPoster = mainThreadSupport != null ? mainThreadSupport.createPoster(this) : null;
        backgroundPoster = new BackgroundPoster(this);
        asyncPoster = new AsyncPoster(this);
        indexCount = builder.subscriberInfoIndexes != null ? builder.subscriberInfoIndexes.size() : 0;
        subscriberMethodFinder = new SubscriberMethodFinder(builder.subscriberInfoIndexes,
                builder.strictMethodVerification, builder.ignoreGeneratedIndex);
        logSubscriberExceptions = builder.logSubscriberExceptions;
        logNoSubscriberMessages = builder.logNoSubscriberMessages;
        sendSubscriberExceptionEvent = builder.sendSubscriberExceptionEvent;
        sendNoSubscriberEvent = builder.sendNoSubscriberEvent;
        throwSubscriberException = builder.throwSubscriberException;
        eventInheritance = builder.eventInheritance;
        executorService = builder.executorService;
    }

    /**
     * Registers the given subscriber to receive events. Subscribers must call {@link #unregister(Object)} once they
     * are no longer interested in receiving events.
     * <p/>
     * Subscribers have event handling methods that must be annotated by {@link Subscribe}.
     * The {@link Subscribe} annotation also allows configuration like {@link
     * ThreadMode} and priority.
     */
    public void register(Object subscriber) {
        //订阅者对象
        Class<?> subscriberClass = subscriber.getClass();
        //找到Subscriber对象中所有订阅方法信息的集合。
        //拿到了订阅方法以及订阅对象的信息后，并保持在SubscriberMethod集合中
        List<SubscriberMethod> subscriberMethods = subscriberMethodFinder.findSubscriberMethods(subscriberClass);
        synchronized (this) {
            //迭代这个集合，然后依次执行subscribe方法
            for (SubscriberMethod subscriberMethod : subscriberMethods) {
                subscribe(subscriber, subscriberMethod);
            }
        }
    }

    /**
     * Must be called in synchronized block
     * 订阅者与订阅者方法关联
     *
     * @param subscriber       订阅者
     * @param subscriberMethod 订阅者中的订阅方法
     */
    private void subscribe(Object subscriber, SubscriberMethod subscriberMethod) {
        //订阅者的订阅方法接收的数据类型
        Class<?> eventType = subscriberMethod.eventType;
        //订阅者的订阅方法接收的操作tag数组
        String[] tags = subscriberMethod.tags;
        Subscription newSubscription = new Subscription(subscriber, subscriberMethod);
        Pair<String[], Subscription> newPair = new Pair<>(tags, newSubscription);
        CopyOnWriteArrayList<Pair<String[], Subscription>> subscriptions = subscriptionsByEventType.get(eventType);
        if (subscriptions == null) {
            subscriptions = new CopyOnWriteArrayList<>();
            subscriptionsByEventType.put(eventType, subscriptions);
        } else {
            if (subscriptions.contains(newPair)) {
                throw new EventBusException("Subscriber " + subscriber.getClass() + " already registered to event "
                        + eventType);
            }
        }

        int size = subscriptions.size();
        //对消息进行优先级排序并添加到关联map的list中
        for (int i = 0; i <= size; i++) {
            if (i == size || subscriberMethod.priority > subscriptions.get(i).second.subscriberMethod.priority) {
                subscriptions.add(i, newPair);
                break;
            }
        }

        List<Class<?>> subscribedEvents = typesBySubscriber.get(subscriber);
        if (subscribedEvents == null) {
            subscribedEvents = new ArrayList<>();
            typesBySubscriber.put(subscriber, subscribedEvents);
        }
        subscribedEvents.add(eventType);

        if (subscriberMethod.sticky) {
            //如果有sticky消息，直接发送。也可以得知sticky消息在注册后是可以执行的。
            if (eventInheritance) {
                //如果支持继承
                // Existing sticky events of all subclasses of eventType have to be considered.
                // Note: Iterating over all events may be inefficient with lots of sticky events,
                // thus data structure should be changed to allow a more efficient lookup
                // (e.g. an additional map storing sub classes of super classes: Class -> List<Class>).
                Set<Map.Entry<Class<?>, Pair<String, Object>>> entries = stickyEvents.entrySet();
                for (Map.Entry<Class<?>, Pair<String, Object>> entry : entries) {
                    Class<?> candidateEventType = entry.getKey();
                    if (eventType.isAssignableFrom(candidateEventType)) {
                        Pair<String, Object> pair = entry.getValue();
                        if (pair != null) {
                            checkPostStickyEventToSubscription(newSubscription, tags, pair.first, pair.second);
                        }
                    }
                }
            } else {
                Pair<String, Object> pair = stickyEvents.get(eventType);
                if (pair != null) {
                    checkPostStickyEventToSubscription(newSubscription, tags, pair.first, pair.second);
                }
            }
        }
    }

    /**
     * 检查发送粘性事件到订阅关系,执行订阅者中的订阅方法
     *
     * @param newSubscription 订阅封装
     * @param tags            订阅封装中可执行的tag数组
     * @param tag             缓存的粘性事件对应的操作tag
     * @param stickyEvent     缓存的粘性事件数据类型
     */
    private void checkPostStickyEventToSubscription(Subscription newSubscription, String[] tags, String tag, Object stickyEvent) {
        if (stickyEvent != null) {
            // If the subscriber is trying to abort the event, it will fail (event is not tracked in posting state)
            // --> Strange corner case, which we don't take care of here.
            //执行每个tag对应的订阅者方法
            for (String item : tags) {
                //循环tag数组,如果数组中有post过来的tag,执行对应的订阅者方法
                if (item != null && item.equals(tag)) {
                    postToSubscription(newSubscription, stickyEvent, tag, isMainThread());
                }
            }
        }
    }

    /**
     * Checks if the current thread is running in the main thread.
     * If there is no main thread support (e.g. non-Android), "true" is always returned. In that case MAIN thread
     * subscribers are always called in posting thread, and BACKGROUND subscribers are always called from a background
     * poster.
     */
    private boolean isMainThread() {
        return mainThreadSupport != null ? mainThreadSupport.isMainThread() : true;
    }

    public synchronized boolean isRegistered(Object subscriber) {
        return typesBySubscriber.containsKey(subscriber);
    }

    /**
     * Only updates subscriptionsByEventType, not typesBySubscriber! Caller must update typesBySubscriber.
     */
    private void unsubscribeByEventType(Object subscriber, Class<?> eventType) {
        List<Pair<String[], Subscription>> subscriptions = subscriptionsByEventType.get(eventType);
        if (subscriptions != null) {
            int size = subscriptions.size();
            for (int i = 0; i < size; i++) {
                Subscription subscription = subscriptions.get(i).second;
                if (subscription.subscriber == subscriber) {
                    subscription.active = false;
                    subscriptions.remove(i);
                    i--;
                    size--;
                }
            }
        }
    }

    /**
     * Unregisters the given subscriber from all event classes.
     */
    public synchronized void unregister(Object subscriber) {
        List<Class<?>> subscribedTypes = typesBySubscriber.get(subscriber);
        if (subscribedTypes != null) {
            for (Class<?> eventType : subscribedTypes) {
                unsubscribeByEventType(subscriber, eventType);
            }
            typesBySubscriber.remove(subscriber);
        } else {
            logger.log(Level.WARNING, "Subscriber to unregister was not registered before: " + subscriber.getClass());
        }
    }

    /**
     * 发送事件
     *
     * @param event 如果是{@link EventBusEvent}子类，根据{@link EventBusEvent#isGlobalCatch()}发送
     */
    public void post(Object event) {
        if (event instanceof EventBusEvent) {
            if (((EventBusEvent) event).isGlobalCatch()) {
                post(event, "defaultTag");
            } else {
                post(event, ((EventBusEvent) event).tag);
            }
        } else {
            post(event, "defaultTag");
        }
    }

    /**
     * Posts the given event to the event bus.
     */
    public void post(Object event, String tag) {
        PostingThreadState postingState = currentPostingThreadState.get();
        List<Object> eventQueue = postingState.eventQueue;
        eventQueue.add(event);
        postingState.tag = tag;

        //判断消息是否已经发送
        if (!postingState.isPosting) {
            //方法执行到底是否在主线程中
            postingState.isMainThread = isMainThread();
            postingState.isPosting = true;
            if (postingState.canceled) {
                //消息是否已经取消
                throw new EventBusException("Internal error. Abort state was not reset");
            }
            try {
                while (!eventQueue.isEmpty()) {
                    //则程序继续往下执行postSingleEvent方法
                    postSingleEvent(eventQueue.remove(0), postingState);
                }
            } finally {
                postingState.isPosting = false;
                postingState.isMainThread = false;
            }
        }
    }

    /**
     * Called from a subscriber's event handling method, further event delivery will be canceled. Subsequent
     * subscribers
     * won't receive the event. Events are usually canceled by higher priority subscribers (see
     * {@link Subscribe#priority()}). Canceling is restricted to event handling methods running in posting thread
     * {@link ThreadMode#POSTING}.
     */
    public void cancelEventDelivery(Object event) {
        PostingThreadState postingState = currentPostingThreadState.get();
        if (!postingState.isPosting) {
            throw new EventBusException(
                    "This method may only be called from inside event handling methods on the posting thread");
        } else if (event == null) {
            throw new EventBusException("Event may not be null");
        } else if (postingState.event != event) {
            throw new EventBusException("Only the currently handled event may be aborted");
        } else if (postingState.subscription.subscriberMethod.threadMode != ThreadMode.POSTING) {
            throw new EventBusException(" event handlers may only abort the incoming event");
        }

        postingState.canceled = true;
    }

    /**
     * 发送粘性事件
     *
     * @param event 如果是{@link EventBusEvent}子类，根据{@link EventBusEvent#isGlobalCatch()}发送
     */
    public void postSticky(Object event) {
        if (event instanceof EventBusEvent) {
            if (((EventBusEvent) event).isGlobalCatch()) {
                postSticky(event, "defaultTag");
            } else {
                postSticky(event, ((EventBusEvent) event).tag);
            }
        } else {
            postSticky(event, "defaultTag");
        }
    }

    /**
     * Posts the given event to the event bus and holds on to the event (because it is sticky). The most recent sticky
     * event of an event's type is kept in memory for future access by subscribers using {@link Subscribe#sticky()}.
     */
    public void postSticky(Object event, String tag) {
        synchronized (stickyEvents) {
            //存储粘性操作
            stickyEvents.put(event.getClass(), Pair.create(tag, event));
        }
        // Should be posted after it is putted, in case the subscriber wants to remove immediately
        post(event, tag);
    }

    /**
     * Gets the most recent sticky event for the given type.
     *
     * @see #postSticky(Object, String)
     */
    public <T> T getStickyEvent(Class<T> eventType) {
        synchronized (stickyEvents) {
            Pair<String, Object> pair = stickyEvents.get(eventType);
            if (pair == null) {
                return null;
            }
            return eventType.cast(pair.second);
        }
    }

    /**
     * Remove and gets the recent sticky event for the given event type.
     *
     * @see #postSticky(Object, String)
     */
    public <T> T removeStickyEvent(Class<T> eventType) {
        synchronized (stickyEvents) {
            Pair<String, Object> pair = stickyEvents.remove(eventType);
            if (pair == null) {
                return null;
            }
            return eventType.cast(pair.second);
        }
    }

    /**
     * Removes the sticky event if it equals to the given event.
     *
     * @return true if the events matched and the sticky event was removed.
     */
    public boolean removeStickyEvent(Object event) {
        synchronized (stickyEvents) {
            Class<?> eventType = event.getClass();
            Pair<String, Object> pair = stickyEvents.remove(eventType);
            if (pair == null) {
                return false;
            }
            if (event.equals(pair.second)) {
                stickyEvents.remove(eventType);
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Removes all sticky events.
     */
    public void removeAllStickyEvents() {
        synchronized (stickyEvents) {
            stickyEvents.clear();
        }
    }

    public boolean hasSubscriberForEvent(Class<?> eventClass) {
        return hasSubscriberForEvent(eventClass, "defaultTag");
    }

    public boolean hasSubscriberForEvent(Class<?> eventClass, String tag) {
        List<Class<?>> eventTypes = lookupAllEventTypes(eventClass);
        if (eventTypes != null) {
            int countTypes = eventTypes.size();
            for (int h = 0; h < countTypes; h++) {
                Class<?> clazz = eventTypes.get(h);
                CopyOnWriteArrayList<Pair<String[], Subscription>> subscriptions;
                synchronized (this) {
                    subscriptions = subscriptionsByEventType.get(clazz);
                }
                if (subscriptions != null && !subscriptions.isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void postSingleEvent(Object event, PostingThreadState postingState) throws Error {
        Class<?> eventClass = event.getClass();
        boolean subscriptionFound = false;
        if (eventInheritance) {
            List<Class<?>> eventTypes = lookupAllEventTypes(eventClass);
            int countTypes = eventTypes.size();
            for (int h = 0; h < countTypes; h++) {
                Class<?> clazz = eventTypes.get(h);
                subscriptionFound |= postSingleEventForEventType(event, postingState, clazz);
            }
        } else {
            subscriptionFound = postSingleEventForEventType(event, postingState, eventClass);
        }
        if (!subscriptionFound) {
            if (logNoSubscriberMessages) {
                logger.log(Level.FINE, "No subscribers registered for event " + eventClass);
            }
            if (sendNoSubscriberEvent && eventClass != NoSubscriberEvent.class &&
                    eventClass != SubscriberExceptionEvent.class) {
                post(new NoSubscriberEvent(this, event), postingState.tag);
            }
        }
    }

    /**
     * 会拿出注册时缓存订阅方法的相关信息subscriptionsByEventType，
     * 并迭代这个Map对象，取出订阅方法的信息，并最终执行postToSubscription方法
     *
     * @param event
     * @param postingState
     * @param eventClass
     * @return
     */
    private boolean postSingleEventForEventType(Object event, PostingThreadState postingState, Class<?> eventClass) {
        CopyOnWriteArrayList<Pair<String[], Subscription>> subscriptions;
        String tag;
        //防止多次调用该方式时,方法内部变量被duo
        synchronized (this) {
            subscriptions = subscriptionsByEventType.get(eventClass);
            tag = postingState.tag;
        }
        if (subscriptions != null && !subscriptions.isEmpty()) {
            for (Pair<String[], Subscription> pair : subscriptions) {
                postingState.event = event;
                postingState.subscription = pair.second;
                boolean aborted = false;
                try {
                    for (String item : pair.first) {
                        //循环tag数组,如果数组中有post过来的tag,执行对应的订阅者方法
                        if (item != null && item.equals(tag)) {
                            postToSubscription(pair.second, event, tag, postingState.isMainThread);
                            aborted = postingState.canceled;
                            break;
                        }
                    }
                } finally {
                    postingState.event = null;
                    postingState.subscription = null;
                    postingState.canceled = false;
                }
                if (aborted) {
                    break;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 根据注解中threadMode的值，判断方法在哪个线程中执行,
     * 无论是在主线程还是子线程中执行，最终都会invokeSubscriber方法,
     * 其实就是利用发射执行Subscriber method，
     * 所以EventBus的post（）方法执行后，最终会执行Subscriber method。
     *
     * @param subscription
     * @param event
     * @param tag
     * @param isMainThread
     */
    private void postToSubscription(Subscription subscription, Object event, String tag, boolean isMainThread) {
        switch (subscription.subscriberMethod.threadMode) {
            case POSTING:
                invokeSubscriber(subscription, event, tag);
                break;
            case MAIN:
                if (isMainThread) {
                    invokeSubscriber(subscription, event, tag);
                } else {
                    mainThreadPoster.enqueue(subscription, event, tag);
                }
                break;
            case MAIN_ORDERED:
                if (mainThreadPoster != null) {
                    mainThreadPoster.enqueue(subscription, event, tag);
                } else {
                    // temporary: technically not correct as poster not decoupled from subscriber
                    invokeSubscriber(subscription, event, tag);
                }
                break;
            case BACKGROUND:
                if (isMainThread) {
                    backgroundPoster.enqueue(subscription, event, tag);
                } else {
                    invokeSubscriber(subscription, event, tag);
                }
                break;
            case ASYNC:
                asyncPoster.enqueue(subscription, event, tag);
                break;
            default:
                throw new IllegalStateException("Unknown thread mode: " + subscription.subscriberMethod.threadMode);
        }
    }

    /**
     * Looks up all Class objects including super classes and interfaces. Should also work for interfaces.
     */
    private static List<Class<?>> lookupAllEventTypes(Class<?> eventClass) {
        synchronized (EVENT_TYPES_CACHE) {
            List<Class<?>> eventTypes = EVENT_TYPES_CACHE.get(eventClass);
            if (eventTypes == null) {
                eventTypes = new ArrayList<>();
                Class<?> clazz = eventClass;
                while (clazz != null) {
                    eventTypes.add(clazz);
                    addInterfaces(eventTypes, clazz.getInterfaces());
                    clazz = clazz.getSuperclass();
                }
                EVENT_TYPES_CACHE.put(eventClass, eventTypes);
            }
            return eventTypes;
        }
    }

    /**
     * Recurses through super interfaces.
     */
    static void addInterfaces(List<Class<?>> eventTypes, Class<?>[] interfaces) {
        for (Class<?> interfaceClass : interfaces) {
            if (!eventTypes.contains(interfaceClass)) {
                eventTypes.add(interfaceClass);
                addInterfaces(eventTypes, interfaceClass.getInterfaces());
            }
        }
    }

    /**
     * Invokes the subscriber if the subscriptions is still active. Skipping subscriptions prevents race conditions
     * between {@link #unregister(Object)} and event delivery. Otherwise the event might be delivered after the
     * subscriber unregistered. This is particularly important for main thread delivery and registrations bound to the
     * live cycle of an Activity or Fragment.
     */
    void invokeSubscriber(PendingPost pendingPost) {
        Object event = pendingPost.event;
        String tag = pendingPost.tag;
        Subscription subscription = pendingPost.subscription;
        PendingPost.releasePendingPost(pendingPost);
        if (subscription.active) {
            invokeSubscriber(subscription, event, tag);
        }
    }

    void invokeSubscriber(Subscription subscription, Object event, String tag) {
        try {
            //通过反射调用订阅者subscriber的方法subscriberMethod,并传入参数event
            subscription.subscriberMethod.method.invoke(subscription.subscriber, event);
            if (subscription.subscriberMethod.sticky) {
                //TODO 如果是粘性通知,完成调用订阅者方法之后,移除粘性订阅
                removeStickyEvent(event);
            }
        } catch (InvocationTargetException e) {
            handleSubscriberException(subscription, event, tag, e.getCause());
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Unexpected exception", e);
        }
    }

    private void handleSubscriberException(Subscription subscription, Object event, String tag, Throwable cause) {
        if (event instanceof SubscriberExceptionEvent) {
            if (logSubscriberExceptions) {
                // Don't send another SubscriberExceptionEvent to avoid infinite event recursion, just log
                logger.log(Level.SEVERE, "SubscriberExceptionEvent subscriber " + subscription.subscriber.getClass()
                        + " threw an exception", cause);
                SubscriberExceptionEvent exEvent = (SubscriberExceptionEvent) event;
                logger.log(Level.SEVERE, "Initial event " + exEvent.causingEvent + " caused exception in "
                        + exEvent.causingSubscriber, exEvent.throwable);
            }
        } else {
            if (throwSubscriberException) {
                throw new EventBusException("Invoking subscriber failed", cause);
            }
            if (logSubscriberExceptions) {
                logger.log(Level.SEVERE, "Could not dispatch event: " + event.getClass() + " to subscribing class "
                        + subscription.subscriber.getClass(), cause);
            }
            if (sendSubscriberExceptionEvent) {
                SubscriberExceptionEvent exEvent = new SubscriberExceptionEvent(this, cause, event,
                        subscription.subscriber);
                post(exEvent, tag);
            }
        }
    }


    /**
     * 当前操作线程的状态
     * For ThreadLocal, much faster to set (and get multiple values).
     */
    final static class PostingThreadState {
        final List<Object> eventQueue = new ArrayList<>();
        boolean isPosting;
        boolean isMainThread;
        Subscription subscription;
        Object event;
        boolean canceled;
        String tag;
    }

    ExecutorService getExecutorService() {
        return executorService;
    }

    /**
     * For internal use only.
     */
    public EventBusLogger getLogger() {
        return logger;
    }

    // Just an idea: we could provide a callback to post() to be notified, an alternative would be events, of course...
    /* public */interface PostCallback {
        void onPostCompleted(List<SubscriberExceptionEvent> exceptionEvents);
    }

    @Override
    public String toString() {
        return "EventBus[indexCount=" + indexCount + ", eventInheritance=" + eventInheritance + "]";
    }
}