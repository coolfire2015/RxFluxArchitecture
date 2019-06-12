package com.huyingbao.module.github.app

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.arch.store.RxAppStore
import com.huyingbao.module.github.ui.login.model.User
import com.huyingbao.module.github.ui.main.action.MainAction
import com.huyingbao.module.github.ui.user.action.UserAction
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubAppStore @Inject constructor(
        application: Application,
        rxDispatcher: RxDispatcher
) : RxAppStore(application, rxDispatcher) {
    val mUser = MutableLiveData<User>()

    override fun onCleared() {
        super.onCleared()
        mUser.value = null
    }

    /**
     * 接收并维持当前登录用户信息
     */
    @Subscribe(tags = [MainAction.GET_LOGIN_USER_INFO, UserAction.UPDATE_USER_INFO])
    fun onReceiveUserInfo(rxAction: RxAction) {
        mUser.value = rxAction.getResponse()
    }
}