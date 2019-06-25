package com.huyingbao.module.github.ui.login.model

import com.google.gson.annotations.SerializedName
import com.huyingbao.module.github.BuildConfig
import java.util.*

/**
 * 登录请求入参，包含申请的Github clientId等数据
 *
 * Created by liujunfeng on 2019/5/30.
 */
class LoginRequest {

    var scopes: List<String>? = null
        private set
    var note: String? = null
        private set
    @SerializedName("client_id")
    var clientId: String? = null
        private set
    @SerializedName("client_secret")
    var clientSecret: String? = null
        private set

    companion object {
        fun generate(): LoginRequest {
            val model = LoginRequest()
            model.scopes = Arrays.asList("user", "repo", "gist", "notifications")
            model.note = BuildConfig.APPLICATION_ID
            model.clientId = BuildConfig.CLIENT_ID
            model.clientSecret = BuildConfig.CLIENT_SECRET
            return model
        }
    }
}
