package com.huyingbao.module.gan.ui.random.action;

import com.huyingbao.core.arch.action.RxActionCreator;
import com.huyingbao.core.arch.action.RxActionManager;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.model.RxAction;
import com.huyingbao.core.arch.scope.ActivityScope;
import com.huyingbao.module.gan.action.GanApi;
import com.huyingbao.module.gan.action.GanConstants;

import javax.inject.Inject;


/**
 * rxAction创建发送管理类
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
@ActivityScope
public class RandomActionCreator extends RxActionCreator implements RandomAction {
    private GanApi mGanApi;

    @Inject
    RandomActionCreator(RxDispatcher rxDispatcher, RxActionManager rxActionManager, GanApi ganApi) {
        super(rxDispatcher, rxActionManager);
        mGanApi = ganApi;
    }

    @Override
    public void getDataList(String category, int count, int page) {
        RxAction rxAction = newRxAction(GET_DATA_LIST,
                GanConstants.Key.COUNT, count,
                GanConstants.Key.PAGE, page);
        postHttpAction(rxAction, mGanApi.getDataList(category, count, page));
    }
}
