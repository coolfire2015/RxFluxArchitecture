package com.huyingbao.core.base

import androidx.appcompat.app.AppCompatActivity
import com.huyingbao.core.base.common.fragment.BaseCommonFragment
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment

/**
 * 设置标题，设置返回图标
 *
 * @param title    Toolbar标题
 * @param backAble true：Home按钮作为返回箭头，false：默认设置
 */
fun AppCompatActivity.setTitle(title: CharSequence?, backAble: Boolean) {
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
fun AppCompatActivity.setTitle(titleId: Int, backAble: Boolean) {
    setTitle(getText(titleId), backAble)
}

/**
 * 设置标题，设置返回图标
 *
 * @param title    Toolbar标题
 * @param backAble true：Home按钮作为返回箭头，false：默认设置
 */
fun BaseFluxFragment<*>.setTitle(title: CharSequence?, backAble: Boolean) {
    activity?.let {
        //宿主Activity中设置标题和返回标志
        if (it is AppCompatActivity) {
            it.setTitle(title, backAble)
        } else {
            it.title = title
        }
        //当前Fragment中暂存标题和返回标志
        this.backAble = backAble
        this.title = title
    }
}

/**
 * 设置标题，设置返回图标
 *
 * @param titleId  Toolbar标题
 * @param backAble true：Home按钮作为返回箭头，false：默认设置
 */
fun BaseFluxFragment<*>.setTitle(titleId: Int, backAble: Boolean) {
    setTitle(getText(titleId), backAble)
}

/**
 * 设置标题，设置返回图标
 *
 * @param title    Toolbar标题
 * @param backAble true：Home按钮作为返回箭头，false：默认设置
 */
fun BaseCommonFragment.setTitle(title: CharSequence?, backAble: Boolean) {
    activity?.let {
        //宿主Activity中设置标题和返回标志
        if (it is AppCompatActivity) {
            it.setTitle(title, backAble)
        } else {
            it.title = title
        }
        //当前Fragment中暂存标题和返回标志
        this.backAble = backAble
        this.title = title
    }
}

/**
 * 设置标题，设置返回图标
 *
 * @param titleId  Toolbar标题
 * @param backAble true：Home按钮作为返回箭头，false：默认设置
 */
fun BaseCommonFragment.setTitle(titleId: Int, backAble: Boolean) {
    setTitle(getText(titleId), backAble)
}