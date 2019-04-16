package com.huyingbao.module.wan.ui.article.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.base.rxview.BaseRxActivity
import com.huyingbao.module.wan.ui.article.action.ArticleAction
import com.huyingbao.module.wan.ui.article.store.ArticleStore
import com.huyingbao.module.wan.ui.friend.view.FriendFragment
import com.huyingbao.module.wan.ui.login.view.LoginActivity
import org.greenrobot.eventbus.Subscribe

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Route(path = "/wan/ArticleActivity")
class ArticleActivity : BaseRxActivity<ArticleStore>() {
    override fun createFragment(): Fragment {
        return ArticleListFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {}

    @Subscribe(tags = [ArticleAction.TO_LOGIN])
    fun toLogin(rxChange: RxChange) {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    @Subscribe(tags = [ArticleAction.TO_FRIEND])
    fun toFriend(rxChange: RxChange) {
        addFragmentHideExisting(FriendFragment.newInstance())
    }

    @Subscribe(tags = [ArticleAction.TO_BANNER])
    fun toBanner(rxChange: RxChange) {
        addFragmentHideExisting(BannerFragment.newInstance())
    }
}
