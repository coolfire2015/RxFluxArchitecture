package com.huyingbao.module.wan.ui.article.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.huyingbao.core.arch.model.Change
import com.huyingbao.module.common.utils.FragmentOp
import com.huyingbao.core.base.flux.activity.BaseFluxFragActivity
import com.huyingbao.module.common.utils.setFragment
import com.huyingbao.module.common.app.CommonAppAction
import com.huyingbao.module.common.app.CommonAppConstants
import com.huyingbao.module.common.utils.addFloatingActionButton
import com.huyingbao.module.common.utils.setAppBarScroll
import com.huyingbao.module.wan.R
import com.huyingbao.module.wan.ui.article.action.ArticleAction
import com.huyingbao.module.wan.ui.article.store.ArticleStore
import com.huyingbao.module.wan.ui.friend.view.FriendFragment
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.find

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Route(path = CommonAppConstants.Router.ArticleActivity)
class ArticleActivity : BaseFluxFragActivity<ArticleStore>() {
    override fun createFragment(): Fragment? {
        return ArticleListFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        //设置联动
        find<Toolbar>(R.id.tlb_top).setAppBarScroll()
        //添加FloatingActionButton
        find<CoordinatorLayout>(R.id.cdl_content)
                .addFloatingActionButton(this, View.OnClickListener {
                    baseActionCreator.postLocalChange(CommonAppAction.SCROLL_TO_TOP)
                })
    }

    @Subscribe(tags = [ArticleAction.TO_FRIEND], sticky = true)
    fun toFriend(change: Change) {
        setFragment(getFragmentContainerId(), FriendFragment.newInstance(), FragmentOp.OP_HIDE)
    }

    @Subscribe(tags = [ArticleAction.TO_BANNER], sticky = true)
    fun toBanner(change: Change) {
        setFragment(getFragmentContainerId(), BannerFragment.newInstance(), FragmentOp.OP_HIDE)
    }
}
