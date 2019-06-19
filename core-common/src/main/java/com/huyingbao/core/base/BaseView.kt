package com.huyingbao.core.base

import android.os.Bundle

import androidx.annotation.LayoutRes

/**
 * View抽象接口
 *
 * Created by liujunfeng on 2019/1/1.
 */
interface BaseView {
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
