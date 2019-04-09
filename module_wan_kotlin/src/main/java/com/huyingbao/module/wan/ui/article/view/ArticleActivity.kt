package com.huyingbao.module.wan.ui.article.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.common.rxview.CommonRxActivity
import com.huyingbao.module.wan.ui.article.action.ArticleAction
import com.huyingbao.module.wan.ui.article.store.ArticleStore
import com.huyingbao.module.wan.ui.friend.view.FriendFragment
import com.huyingbao.module.wan.ui.login.view.LoginActivity
import dagger.Lazy
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Route(path = "/wan/ArticleActivity")
class ArticleActivity : CommonRxActivity<ArticleStore>() {
    @Inject
    lateinit var mArticleListFragmentLazy: Lazy<ArticleListFragment>
    @Inject
    lateinit var mFriendFragmentLazy: Lazy<FriendFragment>
    @Inject
    lateinit var mBannerFragmentLazy: Lazy<BannerFragment>

    override fun createFragment(): Fragment {
        return mArticleListFragmentLazy.get()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {}

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    override fun onRxChanged(rxChange: RxChange) {
        super.onRxChanged(rxChange)
        when (rxChange.tag) {
            ArticleAction.TO_BANNER -> addFragmentHideExisting(mBannerFragmentLazy.get())
            ArticleAction.TO_FRIEND -> addFragmentHideExisting(mFriendFragmentLazy.get())
            ArticleAction.TO_LOGIN -> startActivity(Intent(this, LoginActivity::class.java))
            else -> {
            }
        }
    }
}
