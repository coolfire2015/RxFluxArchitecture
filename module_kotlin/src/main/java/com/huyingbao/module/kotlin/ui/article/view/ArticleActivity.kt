package com.huyingbao.module.kotlin.ui.article.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.common.view.CommonRxActivity
import com.huyingbao.module.kotlin.ui.article.action.ArticleAction
import com.huyingbao.module.kotlin.ui.article.store.ArticleStore
import com.huyingbao.module.kotlin.ui.friend.view.FriendFragment
import com.huyingbao.module.kotlin.ui.login.view.LoginActivity
import dagger.Lazy
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Route(path = "/kotlin/ArticleActivity")
class ArticleActivity : CommonRxActivity<ArticleStore>() {
    @Inject
    internal var mArticleListFragmentLazy: Lazy<ArticleListFragment>? = null
    @Inject
    internal var mFriendFragmentLazy: Lazy<FriendFragment>? = null
    @Inject
    internal var mBannerFragmentLazy: Lazy<BannerFragment>? = null

    override fun createFragment(): Fragment {
        return mArticleListFragmentLazy!!.get()
    }

    override fun afterCreate(savedInstanceState: Bundle) {}

    @Subscribe(sticky = true)
    override fun onRxChanged(rxChange: RxChange) {
        super.onRxChanged(rxChange)
        when (rxChange.tag) {
            ArticleAction.TO_BANNER -> addFragmentHideExisting(mBannerFragmentLazy!!.get())
            ArticleAction.TO_FRIEND -> addFragmentHideExisting(mFriendFragmentLazy!!.get())
            ArticleAction.TO_LOGIN -> startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
