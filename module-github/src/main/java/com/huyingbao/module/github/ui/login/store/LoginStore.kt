package com.huyingbao.module.github.ui.login.store

import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.arch.store.RxActivityStore
import com.huyingbao.core.common.module.CommonContants
import com.huyingbao.core.utils.LocalStorageUtils
import com.huyingbao.module.github.ui.login.action.LoginAction
import com.huyingbao.module.github.ui.login.model.AccessToken
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 登录模块数据处理Store
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
class LoginStore @Inject constructor(rxDispatcher: RxDispatcher) : RxActivityStore(rxDispatcher) {
    @Inject
    lateinit var localStorageUtils: LocalStorageUtils

    @Subscribe(tags = [LoginAction.LOGIN])
    fun onLogin(rxAction: RxAction) {
        val userName: String? = rxAction.data[CommonContants.Key.USER_NAME] as String?
        val password: String? = rxAction.data[CommonContants.Key.PASSWORD] as String?
        localStorageUtils.setValue(CommonContants.Key.ACCESS_TOKEN, rxAction.getResponse<AccessToken?>()?.token)
        localStorageUtils.setValue(CommonContants.Key.USER_NAME, userName)
        localStorageUtils.setValue(CommonContants.Key.PASSWORD, password)
        postChange(RxChange.newInstance(rxAction.tag))
    }

    /**
     * 获取保存的Token
     */
    fun getAccessToken(): String? {
        return localStorageUtils.getValue(CommonContants.Key.ACCESS_TOKEN, "")
    }

    /**
     * 获取保存的用户名
     */
    fun getUserName(): String? {
        return localStorageUtils.getValue(CommonContants.Key.USER_NAME, "")
    }

    /**
     * 获取保存的密码
     */
    fun getPassword(): String? {
        return localStorageUtils.getValue(CommonContants.Key.PASSWORD, "")
    }
}
