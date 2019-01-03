package com.huyingbao.module.gan.ui.category.store;

import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.model.RxAction;
import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.arch.store.RxActivityStore;
import com.huyingbao.module.gan.action.GanConstants;
import com.huyingbao.module.gan.ui.category.action.CategoryAction;

import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@Singleton
public class CategoryStore extends RxActivityStore {
    private String mCategory;

    @Inject
    CategoryStore(RxDispatcher rxDispatcher) {
        super(rxDispatcher);
    }

    /**
     * 当所有者Activity销毁时,框架调用ViewModel的onCleared（）方法，以便它可以清理资源。
     */
    @Override
    protected void onCleared() {
        super.onCleared();
        mCategory = null;
    }

    /**
     * 接收RxAction
     * 处理RxAction携带的数据
     * 发送RxChange通知RxView
     *
     * @param rxAction
     */
    @Override
    @Subscribe()
    public void onRxAction(RxAction rxAction) {
        switch (rxAction.getTag()) {
            case CategoryAction.TO_RANDOM_LIST:
                mCategory = rxAction.get(GanConstants.Key.CATEGORY);
                postChange(RxChange.newRxChange(rxAction.getTag()));
                break;
        }
    }

    public String getCategory() {
        return mCategory;
    }
}
