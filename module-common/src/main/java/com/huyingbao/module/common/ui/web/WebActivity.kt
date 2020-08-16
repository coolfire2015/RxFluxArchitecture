package com.huyingbao.module.common.ui.web

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.huyingbao.core.base.common.activity.BaseCommonFragActivity
import com.huyingbao.core.utils.setTitle
import com.huyingbao.module.common.R
import com.huyingbao.module.common.app.CommonAppConstants
import org.jetbrains.anko.browse
import org.jetbrains.anko.share
import org.jetbrains.anko.toast

/**
 * 显示网页，Menu中有分享和使用浏览器打开
 *
 * Created by liujunfeng on 2019/5/31.
 */
@Route(path = CommonAppConstants.Router.WebActivity)
class WebActivity : BaseCommonFragActivity() {

    private var url: String? = null

    companion object {
        /**
         * @param url   网页的地址
         * @param title 网页的标题
         */
        fun newIntent(context: Context, url: String?, title: String?): Intent {
            return Intent(context, WebActivity::class.java).apply {
                putExtra(CommonAppConstants.Key.URL, url)
                putExtra(CommonAppConstants.Key.TITLE, title)
            }
        }
    }

    override fun createFragment(): Fragment? {
        var fragment: Fragment? = null
        intent.getStringExtra(CommonAppConstants.Key.URL)?.let {
            url = it
            fragment = WebFragment.newInstance(it)
        }
        return fragment
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        //设置当前网页窗口的标题
        intent.getStringExtra(CommonAppConstants.Key.TITLE)?.let {
            setTitle(it, true)
        }
    }

    /**
     * 实例化需要展示的Menu布局
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.common_web, menu)
        return true
    }

    /**
     * MenuItem点击事件
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when {
            //复制
            item.itemId == R.id.common_web_menu_copy -> actionCopy()
            //浏览器打开
            item.itemId == R.id.common_web_menu_browser -> actionBrowser()
            //分享
            item.itemId == R.id.common_web_menu_share -> actionShare()
            //其他
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * 复制
     */
    private fun actionCopy(): Boolean {
        url?.let {
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clip = ClipData.newPlainText("", it)
            clipboardManager.setPrimaryClip(clip)
            toast(R.string.common_toast_copy)
        }
        return true
    }

    /**
     * 浏览器打开
     */
    private fun actionBrowser(): Boolean {
        url?.let { browse(it) }
        return true
    }

    /**
     * 分享
     */
    private fun actionShare(): Boolean {
        url?.let { share(it) }
        return true
    }
}
