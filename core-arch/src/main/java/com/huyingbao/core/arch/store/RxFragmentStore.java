package com.huyingbao.core.arch.store;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import com.huyingbao.core.arch.RxFlux;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.model.RxChange;


/**
 * RxStore是一个抽象类,注册到dispatcher中,用来管理订阅,按顺序接收actions.
 * 这个类必须被应用的每个store扩展实现,来接收被调度传递过来的actions
 * <p>
 * 每一个Store仅仅负责一片逻辑相关的UI区域，用来维护这片UI的状态，
 * Store对外仅仅提供get方法，它的更新通过Dispatcher派发的Action来更新，
 * 当有新的Action进来的时候，它会负责处理Action，并转化成UI需要的数据。
 * 所有的数据都通过Dispatcher这个枢纽中心传递。
 * <p>
 * Action通过ActionCreator的帮助类产生并传递给Dispatcher，
 * Action大部分情况下是在用户和View交互的时候产生。
 * 然后Dispatcher会调用Store注册在其(RxDispatcher)中的回调方法,
 * 把Action发送到所有注册的Store。
 * <p>
 * 在Store的回调方法内，Store可以处理任何和自身状态有关联的Action。
 * Store接着会触发一个 change 事件来告知Controller-View数据层发生变化。
 * <p>
 * RxStore继承ViewModel，可以在屏幕旋转或配置更改引起的Activity重建时存活下来。
 * 重建后数据可以继续使用。
 * <p>
 * 实现{@link LifecycleObserver}，可以关联Fragment生命周期
 * <p>
 * ViewModel类旨在存储和管理与UI相关的数据，
 * 以便数据在诸如屏幕旋转之类的配置更改中生存下来。
 * 它还处理Fragment与应用程序的其余部分的通信（例如调用业务逻辑类）。
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public abstract class RxFragmentStore extends ViewModel implements LifecycleObserver, RxStore {
    private final RxDispatcher mRxDispatcher;

    public RxFragmentStore(RxDispatcher rxDispatcher) {
        this.mRxDispatcher = rxDispatcher;
    }

    /**
     * 所关联对象（Fragment）创建时调用该方法
     * 需要将store注册到dispatcher中
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void subscribe() {
        if (mRxDispatcher.isSubscribe(this)) {
            return;
        }
        Log.i(RxFlux.TAG, "Subscribe RxFragmentStore : " + getClass().getSimpleName());
        mRxDispatcher.subscribeRxStore(this);
    }


    /**
     * 所关联对象（Fragment）销毁时调用该方法
     * 从dispatcher中取消订阅
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void unsubscribe() {
        Log.i(RxFlux.TAG, "Unsubscribe RxFragmentStore : " + getClass().getSimpleName());
        mRxDispatcher.unsubscribeRxStore(this);
    }

    /**
     * 传递更改,传递一个RxStoreChange,
     * 每一个RxStoreChange由storeId和action组成
     *
     * @param change
     */
    @Override
    public void postChange(RxChange change) {
        mRxDispatcher.postRxChange(change);
    }
}
