package com.huyingbao.core.common.view

import android.os.Bundle

/**
 * Created by liujunfeng on 2019/1/1.
 */
interface CommonView {
    val layoutId: Int

    fun afterCreate(savedInstanceState: Bundle)
}
