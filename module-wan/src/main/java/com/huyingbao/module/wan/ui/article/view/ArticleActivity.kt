package com.huyingbao.module.wan.ui.article.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.arch.model.RxLoading
import com.huyingbao.core.base.flux.activity.BaseFluxFragActivity
import com.huyingbao.core.common.module.CommonContants
import com.huyingbao.module.wan.ui.article.action.ArticleAction
import com.huyingbao.module.wan.ui.article.store.ArticleStore
import com.huyingbao.module.wan.ui.friend.view.FriendFragment
import org.greenrobot.eventbus.Subscribe

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Route(path = CommonContants.Address.ArticleActivity)
class ArticleActivity : BaseFluxFragActivity<ArticleStore>() {
    override fun createFragment(): Fragment? {
        return ArticleListFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {}

    @Subscribe(tags = [ArticleAction.TO_FRIEND], sticky = true)
    fun toFriend(rxChange: RxChange) {
        addFragmentHideExisting(FriendFragment.newInstance())
    }

    @Subscribe(tags = [ArticleAction.TO_BANNER], sticky = true)
    fun toBanner(rxChange: RxChange) {
        addFragmentHideExisting(BannerFragment.newInstance())
    }

    /**
     * 拦截全局进度展示，Fragment中使用自己的进度展示
     */
    @Subscribe(sticky = true)
    override fun onRxLoading(rxLoading: RxLoading) {
    }
}
