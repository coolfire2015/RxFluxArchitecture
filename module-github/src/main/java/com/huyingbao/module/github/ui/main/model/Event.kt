package com.huyingbao.module.github.ui.main.model

import com.google.gson.annotations.SerializedName
import com.huyingbao.module.github.ui.login.model.User
import com.shuyu.github.kotlin.model.bean.Repository
import java.util.*

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