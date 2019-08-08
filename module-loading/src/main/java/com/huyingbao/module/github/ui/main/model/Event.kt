package com.huyingbao.module.github.ui.main.model

import com.google.gson.annotations.SerializedName
import com.huyingbao.module.github.ui.login.model.User
import com.shuyu.github.kotlin.model.bean.Repository
import java.util.*

/**
 * 事件类
 * Created by guoshuyu
 * Date: 2018-10-29
 */
class Event {
    var id: String? = null
    var type: String? = null
    var actor: User? = null
    var repo: Repository? = null
    var org: User? = null
    var payload: EventPayload? = null
    @SerializedName("public")
    var isPublic: Boolean = false
    @SerializedName("created_at")
    var createdAt: Date? = null

}