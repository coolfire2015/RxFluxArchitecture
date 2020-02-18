package com.huyingbao.core.base

import android.os.Bundle
import androidx.annotation.IdRes

import androidx.annotation.LayoutRes

/**
 * View抽象接口
 *
 * Created by liujunfeng on 2019/1/1.
 */
interface BaseView {
    /**
     * 获取Toolbar ID，使用默认布局中的ID，可以覆盖该方法，使用自定义ID
     *
     * Activity中调用该方法
     */
    @IdRes
    fun getToolbarId(): Int = R.id.tlb_top

    /**
     * 获取可以容纳Fragment的View ID，使用默认布局中的ID，可以覆盖该方法，使用自定义ID
     *
     * Activity中调用该方法
     */
    @IdRes
    fun getFragmentContainerId(): Int = R.id.fragment_container

    /**
     * 获取对应布局文件ID
     */
    @LayoutRes
    fun getLayoutId(): Int

    /**
     * View层创建之后调用方法
     */
    fun afterCreate(savedInstanceState: Bundle?)
}
