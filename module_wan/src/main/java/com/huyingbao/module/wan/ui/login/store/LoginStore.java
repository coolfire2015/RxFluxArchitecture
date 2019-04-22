package com.huyingbao.module.wan.ui.login.store;

import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.model.RxAction;
import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.arch.store.RxActivityStore;
import com.huyingbao.module.wan.action.WanResponse;
import com.huyingbao.module.wan.ui.login.action.LoginAction;
import com.huyingbao.module.wan.ui.login.model.User;

import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.MutableLiveData;

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
public class LoginStore extends RxActivityStore {
    private MutableLiveData<String> mIntervalLiveData = new MutableLiveData<>();
    private WanResponse<User> mUser;

    @Inject
    LoginStore(RxDispatcher rxDispatcher) {
        super(rxDispatcher);
    }

    /**
     * 当所有者Activity销毁时,框架调用ViewModel的onCleared（）方法，以便它可以清理资源。
     */
    @Override
    protected void onCleared() {
        super.onCleared();
        mIntervalLiveData.setValue(null);
    }

    @Subscribe(tags={LoginAction.LOGIN})
    public void onLogin(RxAction rxAction) {
        mUser = rxAction.getResponse();
        postChange(RxChange.newInstance(rxAction));
    }

    @Subscribe(tags={LoginAction.REGISTER})
    public void onRegister(RxAction rxAction) {
        mUser = rxAction.getResponse();
        postChange(RxChange.newInstance(rxAction));
    }

    @Subscribe(tags={LoginAction.GET_IDENTIFY})
    public void setIntervalLiveData(RxAction rxAction) {
        mIntervalLiveData.setValue(rxAction.getResponse() + "");
    }

    public MutableLiveData<String> getIntervalLiveData() {
        return mIntervalLiveData;
    }
}
