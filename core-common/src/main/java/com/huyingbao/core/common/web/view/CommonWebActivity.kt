package com.huyingbao.core.common.web.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.MenuRes
import androidx.fragment.app.Fragment
import com.huyingbao.core.base.activity.BaseRxFragActivity
import com.huyingbao.core.common.module.CommonContants
import com.huyingbao.core.common.web.store.CommonWebStore
import com.orhanobut.logger.Logger

/**
 * 显示网页，使用通用Menu
 */
class CommonWebActivity : BaseRxFragActivity<CommonWebStore>() {

    companion object {
        fun newIntent(context: Context, url: String?, title: String): Intent {
            return newIntent(context, url, title, null, 0)
        }

        fun newIntent(context: Context, url: String?, title: String?, id: String?, @MenuRes menu: Int?): Intent {
            return Intent(context, CommonWebActivity::class.java).apply {
                putExtra(CommonContants.Key.WEB_URL, url)
                putExtra(CommonContants.Key.WEB_TITLE, title)
                putExtra(CommonContants.Key.WEB_ID, id)
                putExtra(CommonContants.Key.WEB_MENU, menu ?: 0)
            }
        }
    }

    override fun createFragment(): Fragment? {
        return CommonWebFragment.newInstance(intent.getStringExtra(CommonContants.Key.WEB_URL))
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        val webTitle = intent.getStringExtra(CommonContants.Key.WEB_TITLE)
        webTitle?.let { setTitle(webTitle, true) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val webMenu = intent.getIntExtra(CommonContants.Key.WEB_MENU, 0)
        if (webMenu != 0) {
            menuInflater.inflate(webMenu, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Logger.e((item.title ?: "空") as String)
        return super.onOptionsItemSelected(item)
    }
}
