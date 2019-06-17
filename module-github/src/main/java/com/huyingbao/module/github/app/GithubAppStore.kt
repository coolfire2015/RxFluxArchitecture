package com.huyingbao.module.github.app

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.arch.store.RxAppStore
import com.huyingbao.module.github.ui.login.action.LoginAction
import com.huyingbao.module.github.ui.login.model.User
import com.huyingbao.module.github.ui.user.action.UserAction
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 全局数据维持AppStore
 *
 * Created by liujunfeng on 2019/6/10.
 */
@Singleton
class GithubAppStore @Inject constructor(
        application: Application,
        rxDispatcher: RxDispatcher
) : RxAppStore(application, rxDispatcher) {
    val userLiveData = MutableLiveData<User>()

    override fun onCleared() {
        super.onCleared()
        userLiveData.value = null
    }

    /**
     * 接收并维持当前登录用户信息
     */
    @Subscribe(tags = [
        LoginAction.GET_LOGIN_USER_INFO,
        UserAction.UPDATE_USER_INFO])
    fun onReceiveUserInfo(rxAction: RxAction) {
        userLiveData.value = rxAction.getResponse()
        postChange(RxChange.newInstance(rxAction.tag))
    }
}