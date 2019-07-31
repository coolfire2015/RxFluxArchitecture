package com.huyingbao.core.web

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.annotation.MenuRes
import com.alibaba.android.arouter.launcher.ARouter
import com.huyingbao.core.arch.view.RxSubscriberView
import com.huyingbao.core.base.BaseView
import com.huyingbao.core.base.fragment.BaseCommonFragment
import com.huyingbao.core.common.R
import com.huyingbao.core.common.module.CommonContants
import kotlinx.android.synthetic.main.common_fragment_web.*

/**
 * Created by liujunfeng on 2019/1/1.
 */
class CommonWebFragment : BaseCommonFragment(), BaseView, RxSubscriberView {
    companion object {
        fun newInstance(url: String, title: String?, id: String?, @MenuRes menu: Int?): CommonWebFragment {
            return CommonWebFragment().apply {
                arguments = Bundle().apply {
                    putString(CommonContants.Key.WEB_URL, url)
                    putString(CommonContants.Key.WEB_TITLE, title)
                    putString(CommonContants.Key.WEB_ID, id)
                    putInt(CommonContants.Key.WEB_MENU, menu ?: 0)
                }
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.common_fragment_web
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        arguments?.let {
            if (it.containsKey(CommonContants.Key.WEB_TITLE)) {
                setTitle(it.getString(CommonContants.Key.WEB_TITLE), true)
            }
            if (it.containsKey(CommonContants.Key.WEB_URL)) {
                web_content.loadUrl(it.getString(CommonContants.Key.WEB_URL))
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        arguments?.let {
            val menuId = it.getInt(CommonContants.Key.WEB_MENU, 0)
            if (menuId != 0) {
                //Menu布局文件名同界面布局文件名
                inflater.inflate(menuId, menu)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when {
            item.itemId == R.id.menu_to_banner -> {
                articleActionCreator.postLocalChange(ArticleAction.TO_BANNER)
                true
            }
            item.itemId == R.id.menu_to_friend -> {
                com.p(ArticleAction.TO_FRIEND)
                true
            }
            item.itemId == R.id.menu_to_gan -> {
                //跳转module-gan
                ARouter.getInstance().build(CommonContants.Address.RandomActivity).navigation()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
