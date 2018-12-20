package com.huyingbao.core.arch.store;

import com.huyingbao.core.arch.action.RxAction;

/**
 * 所有的Store必须实现该接口
 * Created by liujunfeng on 2017/12/7.
 */
public interface RxStoreActionDispatch {
    /**
     * store在接收到RxAction时,调用该方法
     *
     * @param action
     */
    void onRxAction(RxAction action);
}
