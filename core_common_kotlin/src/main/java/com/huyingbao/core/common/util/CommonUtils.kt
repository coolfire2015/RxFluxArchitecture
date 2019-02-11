package com.huyingbao.core.common.util

import android.app.Application
import android.widget.Toast

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by liujunfeng on 2018/12/28.
 */
@Singleton
class CommonUtils @Inject
internal constructor(mApplication: Application) {

    private val toast: Toast

    init {
        toast = Toast.makeText(mApplication, "", Toast.LENGTH_SHORT)
    }


    /**
     * 显示短暂的Toast
     *
     * @param text
     */
    fun showShortToast(text: String) {
        toast.cancel()
        toast.setText(text)
        toast.show()
    }
}
