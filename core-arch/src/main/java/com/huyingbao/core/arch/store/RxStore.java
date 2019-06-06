package com.huyingbao.core.arch.store;

import com.huyingbao.core.arch.model.RxChange;

/**
 * 所有Store都需要实现的接口：
 * <p>
 * 1.存储和管理与UI相关的数据，负责数个逻辑相关{@link com.huyingbao.core.arch.view.RxFluxView}状态，对外提供get方法。
 * <p>
 * 2.接收{@link com.huyingbao.core.arch.dispatcher.RxDispatcher}发送的{@link com.huyingbao.core.arch.model.RxAction}。
 * <p>
 * 3.发送{@link RxChange}给{@link com.huyingbao.core.arch.view.RxFluxView}。
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public interface RxStore {
    /**
     * 发送{@link RxChange}给{@link com.huyingbao.core.arch.view.RxFluxView}
     */
    void postChange(RxChange rxChange);
}
