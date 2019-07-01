package com.huyingbao.module.github.ui.main.model

import com.google.gson.annotations.SerializedName
import com.huyingbao.module.github.ui.login.model.User
import java.util.*

/**
 * 问题类
 * Created by guoshuyu
 * Date: 2018-10-29
 */
class IssueEvent {
    var id: String? = null
    var user: User? = null
    @SerializedName("created_at")
    var createdAt: Date? = null
    @SerializedName("updated_at")
    var updatedAt: Date? = null
    var body: String? = null
    @SerializedName("body_html")
    var bodyHtml: String? = null
    @SerializedName("event")
    var type: String? = null
    @SerializedName("html_url")
    var htmlUrl: String? = null
}
