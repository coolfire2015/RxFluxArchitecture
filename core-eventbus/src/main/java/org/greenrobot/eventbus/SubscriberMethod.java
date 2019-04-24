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

import java.lang.reflect.Method;

/**
 * Used internally by EventBus and generated subscriber indexes.
 * 我们在订阅者中定义的订阅方法的一个描述类，里面保存了订阅方法的信息，
 * 其实就是我们在订阅者中定义的用@Subscriber注解修饰的订阅方法。
 */
public class SubscriberMethod {
    /**
     * 方法名
     */
    final Method method;
    /**
     * 方法名对应的tag数组
     */
    final String[] tags;
    /**
     * 线程
     */
    final ThreadMode threadMode;
    /**
     * 消息类型的字节码对象
     */
    final Class<?> eventType;
    /**
     * 注解中priority属性，表示方法执行的优先级
     */
    final int priority;
    /**
     * 注解中的sticky, 粘性属性
     */
    final boolean sticky;
    /**
     * Used for efficient comparison
     */
    String methodString;

    public SubscriberMethod(Method method, String[] tags, Class<?> eventType, ThreadMode threadMode, int priority, boolean sticky) {
        this.method = method;
        this.tags = tags;
        this.threadMode = threadMode;
        this.eventType = eventType;
        this.priority = priority;
        this.sticky = sticky;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof SubscriberMethod) {
            checkMethodString();
            SubscriberMethod otherSubscriberMethod = (SubscriberMethod) other;
            otherSubscriberMethod.checkMethodString();
            // Don't use method.equals because of http://code.google.com/p/android/issues/detail?id=7811#c6
            return methodString.equals(otherSubscriberMethod.methodString);
        } else {
            return false;
        }
    }

    private synchronized void checkMethodString() {
        if (methodString == null) {
            // Method.toString has more overhead, just take relevant parts of the method
            StringBuilder builder = new StringBuilder(64);
            builder.append(method.getDeclaringClass().getName());
            builder.append('#').append(method.getName());
            builder.append('(').append(eventType.getName());
            methodString = builder.toString();
        }
    }

    @Override
    public int hashCode() {
        return method.hashCode();
    }
}