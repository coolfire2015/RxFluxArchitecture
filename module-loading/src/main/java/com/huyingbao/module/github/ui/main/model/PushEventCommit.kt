package com.huyingbao.module.github.ui.main.model

import com.huyingbao.module.github.ui.login.model.User

/**
 * 提交类
 * Created by guoshuyu
 * Date: 2018-10-29
 */
class PushEventCommit {
    var sha: String? = null
    //email&name
    var author: User? = null
    var message: String? = null
    var distinct: Boolean = false
    var url: String? = null
}
