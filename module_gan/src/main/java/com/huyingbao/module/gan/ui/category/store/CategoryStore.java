package com.huyingbao.module.gan.ui.category.store;

import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.model.RxAction;
import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.arch.store.RxStore;
import com.huyingbao.module.gan.action.GanConstants;
import com.huyingbao.module.gan.ui.category.action.CategoryAction;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@Singleton
public class CategoryStore extends RxStore {
    private String mCategory;

    @Inject
    CategoryStore(RxDispatcher rxDispatcher) {
        super(rxDispatcher);
    }

    public String getCategory() {
        return mCategory;
    }

    /**
     * postChange(new RxChange(getClass().getSimpleName(), action));
     *
     * @param action
     */
    @Subscribe(tags = {@Tag(CategoryAction.TO_RANDOM_LIST)})
    public void setCategory(RxAction action) {
        mCategory = action.get(GanConstants.Key.CATEGORY);
        postChange(RxChange.newRxChange(action.getTag()));
    }
}
