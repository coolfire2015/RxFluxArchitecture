package com.huyingbao.core.base.common.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.huyingbao.core.base.BaseView


/**
 * 不使用Dagger.Android，不持有ViewModel，不自动管理订阅
 *
 * 带有[Toolbar]的Activity父类
 *
 * Created by liujunfeng on 2019/1/1.
 */
abstract class BaseCommonActivity :
        AppCompatActivity(),
        BaseView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        //使用Toolbar取代原本的actionbar
        window.decorView.findViewById<Toolbar>(getToolbarId())?.let { setSupportActionBar(it) }
        afterCreate(savedInstanceState)
    }

    /**
     * [Toolbar]Menu点击事件，拦截返回按钮，如果Fragment回退栈不为空，先弹出Fragment
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // 点击返回图标事件
            android.R.id.home -> {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    supportFragmentManager.popBackStack()
                    return true
                }
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
