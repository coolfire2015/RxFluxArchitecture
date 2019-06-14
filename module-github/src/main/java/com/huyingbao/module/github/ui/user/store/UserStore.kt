package com.huyingbao.module.github.ui.user.store

import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.arch.store.RxActivityStore
import com.huyingbao.core.common.module.CommonContants
import com.huyingbao.module.github.ui.user.action.UserAction
import com.huyingbao.module.github.ui.user.model.UserInfoRequest
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 当前登录用户模块
 *
 * Created by liujunfeng on 2019/6/10.
 */
@Singleton
class UserStore @Inject constructor(rxDispatcher: RxDispatcher?) : RxActivityStore(rxDispatcher) {
    var mUserInfoRequest: UserInfoRequest? = null
        private set

    override fun onCleared() {
        super.onCleared()
        mUserInfoRequest = null
    }

    /**
     * 接收编辑框内更新的内容，并修改用户信息
     */
    @Subscribe(sticky = true, tags = [
        UserAction.UPDATE_USER_NAME,
        UserAction.UPDATE_USER_EMAIL,
        UserAction.UPDATE_USER_LOCATION,
        UserAction.UPDATE_USER_BLOG,
        UserAction.UPDATE_USER_COMPANY,
        UserAction.UPDATE_USER_BIO])
    fun onUpdateContent(rxAction: RxAction) {
        val userInfoRequest = UserInfoRequest()
        when (rxAction.tag) {
            UserAction.UPDATE_USER_NAME -> userInfoRequest.name = rxAction.data[CommonContants.Key.CONTENT].toString()
            UserAction.UPDATE_USER_EMAIL -> userInfoRequest.email = rxAction.data[CommonContants.Key.CONTENT].toString()
            UserAction.UPDATE_USER_LOCATION -> userInfoRequest.location = rxAction.data[CommonContants.Key.CONTENT].toString()
            UserAction.UPDATE_USER_BLOG -> userInfoRequest.blog = rxAction.data[CommonContants.Key.CONTENT].toString()
            UserAction.UPDATE_USER_COMPANY -> userInfoRequest.company = rxAction.data[CommonContants.Key.CONTENT].toString()
            UserAction.UPDATE_USER_BIO -> userInfoRequest.bio = rxAction.data[CommonContants.Key.CONTENT].toString()
        }
        mUserInfoRequest = userInfoRequest
        //通知View更新
        postChange(RxChange.newInstance(rxAction.tag))
    }
}