package com.huyingbao.core.base.common.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import com.huyingbao.core.base.BaseView
import com.huyingbao.core.base.R


/**
 * 带有[Toolbar]的Activity父类，不使用依赖注入，不
 *
 * Created by liujunfeng on 2019/1/1.
 */
abstract class BaseCommonActivity :
        AppCompatActivity(),
        BaseView {
    init {
        //Vector使用
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initActionBar()
        afterCreate(savedInstanceState)
    }

    /**
     * [Toolbar]Menu点击事件，拦截返回按钮
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // 点击返回图标事件
            android.R.id.home -> {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    //如果Fragment回退栈不为空，先弹出Fragment
                    supportFragmentManager.popBackStack()
                    return true
                }
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    /**
     * 默认使用[Toolbar]取代ActionBar
     */
    private fun initActionBar() {
        val view = window.decorView
        val toolbar = view.findViewById<Toolbar>(R.id.tlb_top)
        if (toolbar != null) {
            //使用Toolbar取代原本的actionbar
            setSupportActionBar(toolbar)
        }
    }

    /**
     * 设置标题，设置返回图标
     *
     * @param title    Toolbar标题
     * @param backAble true：Home按钮作为返回箭头，false：默认设置
     */
    fun setTitle(title: CharSequence?, backAble: Boolean) {
        if (supportActionBar == null) {
            setTitle(title)
        } else {
            supportActionBar?.run {
                //显示标题
                setDisplayShowTitleEnabled(true)
                //设置标题
                this.title = title
                //显示右侧返回图标
                if (backAble) {
                    //显示Home按钮
                    setDisplayShowHomeEnabled(true)
                    //设置Home按钮作为返回按钮
                    setDisplayHomeAsUpEnabled(true)
                    //设置Home按钮图标
                    setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_material)
                }
            }
        }
    }

    /**
     * 设置标题，设置返回图标
     *
     * @param titleId  Toolbar标题
     * @param backAble true：Home按钮作为返回箭头，false：默认设置
     */
    fun setTitle(titleId: Int, backAble: Boolean) {
        setTitle(getText(titleId), backAble)
    }
}
