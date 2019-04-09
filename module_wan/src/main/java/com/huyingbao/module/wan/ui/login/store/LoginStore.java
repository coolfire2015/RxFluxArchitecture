package com.huyingbao.module.wan.ui.login.store;

import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.model.RxAction;
import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.arch.store.RxActivityStore;
import com.huyingbao.module.wan.action.WanResponse;
import com.huyingbao.module.wan.ui.login.action.LoginAction;
import com.huyingbao.module.wan.ui.login.model.User;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRxAction(RxAction rxAction) {
        switch (rxAction.getTag()) {
            case LoginAction.LOGIN:
                mUser = rxAction.getResponse();
                postChange(RxChange.newInstance(rxAction));
                break;
            case LoginAction.REGISTER:
                break;
            case LoginAction.GET_IDENTIFY:
                mIntervalLiveData.setValue(rxAction.getResponse() + "");
                break;
            default:
                break;
        }
    }

    public MutableLiveData<String> getIntervalLiveData() {
        return mIntervalLiveData;
    }
}
