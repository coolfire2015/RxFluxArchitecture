package com.huyingbao.module.github.ui.main.action

import android.net.Uri
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.module.github.api.IssueApi
import com.huyingbao.module.github.api.UserApi
import com.huyingbao.module.github.app.GithubActionCreator
import com.huyingbao.module.github.ui.issue.model.Issue
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * 主页模块
 *
 * Created by liujunfeng on 2019/6/10.
 */
@ActivityScope
class MainActionCreator @Inject constructor(
        rxDispatcher: RxDispatcher,
        rxActionManager: RxActionManager,
        private val retrofit: Retrofit
) : GithubActionCreator(rxDispatcher, rxActionManager), MainAction {
    override fun getLoginUserInfo() {
        val rxAction = newRxAction(MainAction.GET_LOGIN_USER_INFO)
        postHttpLoadingAction(rxAction, retrofit.create(UserApi::class.java).getLoginUserInfo())
    }

    override fun feedback(editContent: String) {
        val rxAction = newRxAction(MainAction.FEED_BACK)
        val issue = Issue()
        issue.title = "用户反馈"
        issue.body = editContent
        postHttpLoadingAction(rxAction, retrofit.create(IssueApi::class.java).createIssue(
                "coolfire2015",
                "RxFluxArchitecture",
                issue
        ))
    }

    override fun getNewsEvent(user: String, page: Int) {
        val rxAction = newRxAction(MainAction.GET_NEWS_EVENT)
        postHttpAction(rxAction, retrofit.create(UserApi::class.java).getNewsEvent(user, page))
    }
}