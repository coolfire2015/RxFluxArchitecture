package com.huyingbao.module.wan.ui.article.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.huyingbao.core.arch.model.Change
import com.huyingbao.core.arch.view.FluxView
import com.huyingbao.core.base.BaseFragActivity
import com.huyingbao.module.common.app.CommonAppConstants
import com.huyingbao.module.wan.ui.article.action.ArticleAction
import com.huyingbao.module.wan.ui.article.store.ArticleStore
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.Subscribe

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Route(path = CommonAppConstants.Router.ArticleActivity)
@AndroidEntryPoint
class ArticleActivity : FluxView, BaseFragActivity() {
    override val store: ArticleStore by viewModels()

    override fun createFragment(): Fragment? {
        return ArticleListFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        //设置联动
//        findViewById<Toolbar>(R.id.).setAppBarScroll()
        //添加FloatingActionButton
//        findViewById<CoordinatorLayout>(R.id.cdl_content)
//                .addFloatingActionButton(this, View.OnClickListener {
//                    baseActionCreator.postLocalChange(CommonAppAction.SCROLL_TO_TOP)
//                })
    }

    @Subscribe(tags = [ArticleAction.TO_FRIEND], sticky = true)
    fun toFriend(change: Change) {
//        setFragment(getFragmentContainerId(), FriendFragment.newInstance(), FragmentOp.OP_HIDE)
    }

    @Subscribe(tags = [ArticleAction.TO_BANNER], sticky = true)
    fun toBanner(change: Change) {
//        setFragment(getFragmentContainerId(), BannerFragment.newInstance(), FragmentOp.OP_HIDE)
    }
}
