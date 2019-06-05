package com.huyingbao.module.github.ui.login.action

import android.util.Base64
import com.huyingbao.core.arch.action.RxActionCreator
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.core.common.module.CommonContants
import com.huyingbao.module.github.utils.FlatMapResponse2Result
import com.huyingbao.module.github.api.UserApi
import com.huyingbao.module.github.ui.login.action.LoginAction.Companion.LOGIN
import com.huyingbao.module.github.ui.login.model.LoginRequest
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by liujunfeng on 2019/1/1.
 */
@ActivityScope
class LoginActionCreator @Inject constructor(
        rxDispatcher: RxDispatcher,
        rxActionManager: RxActionManager,
        private val retrofit: Retrofit
) : RxActionCreator(rxDispatcher, rxActionManager), LoginAction {

    override fun login(username: String, password: String) {
        // 生成RxAction实例
        val rxAction = newRxAction(LOGIN,
                CommonContants.Key.USER_NAME, username,
                CommonContants.Key.PASSWORD, password)
        // 用户名密码转换,可以链式转换
        val basicCode = Base64
                .encodeToString("$username:$password".toByteArray(), Base64.NO_WRAP)
                .replace("\\+", "%2B")
        // 链式调用接口
        val login = retrofit.create(UserApi::class.java)
                // 调用接口1：Auth认证，获取登录token
                .authorizations("Basic $basicCode", LoginRequest.generate())
                .flatMap { FlatMapResponse2Result(it) }
        postHttpAction(rxAction, login)
    }
}
