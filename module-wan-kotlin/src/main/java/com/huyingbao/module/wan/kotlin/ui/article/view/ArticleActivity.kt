package com.huyingbao.module.wan.kotlin.ui.article.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.base.view.BaseActivity
import com.huyingbao.module.wan.kotlin.ui.article.action.ArticleAction
import com.huyingbao.module.wan.kotlin.ui.article.store.ArticleStore
import com.huyingbao.module.wan.kotlin.ui.friend.view.FriendFragment
import com.huyingbao.module.wan.kotlin.ui.login.view.LoginActivity
import org.greenrobot.eventbus.Subscribe

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Route(path = "/wankotlin/ArticleActivity")
class ArticleActivity : BaseActivity<ArticleStore>() {
    override fun createFragment(): Fragment {
        return ArticleListFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {}

    @Subscribe(tags = [ArticleAction.TO_LOGIN], sticky = true)
    fun toLogin(rxChange: RxChange) {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    @Subscribe(tags = [ArticleAction.TO_FRIEND], sticky = true)
    fun toFriend(rxChange: RxChange) {
        addFragmentHideExisting(FriendFragment.newInstance())
    }

    @Subscribe(tags = [ArticleAction.TO_BANNER], sticky = true)
    fun toBanner(rxChange: RxChange) {
        addFragmentHideExisting(BannerFragment.newInstance())
    }
}
