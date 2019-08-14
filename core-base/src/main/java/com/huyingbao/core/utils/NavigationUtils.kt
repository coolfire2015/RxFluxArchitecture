package com.huyingbao.core.utils

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.Navigation

/**
 * Navigation工具方法
 *
 * Created by liujunfeng on 2019/1/1.
 */
object NavigationUtils {
    /**
     * 使用[Navigation]跳转下一个页面，不传参数，不弹出当前页面，不结束当前Activity
     */
    fun navigate(view: View, actionId: Int) {
        navigate(view, actionId, null, popUp = false, finishStack = false)
    }

    /**
     * 使用[Navigation]跳转下一个页面，不传参数，不结束当前Activity
     * @param popUp         true：弹出当前页面
     */
    fun navigate(view: View, actionId: Int, popUp: Boolean) {
        navigate(view, actionId, null, popUp, finishStack = false)
    }

    /**
     * 使用[Navigation]跳转下一个页面，不结束当前Activity
     * @param args          传给Fragment的入参
     * @param popUp         true：弹出当前页面
     */
    fun navigate(view: View, actionId: Int, args: Bundle?, popUp: Boolean) {
        navigate(view, actionId, args, popUp, finishStack = false)
    }

    /**
     * 使用[Navigation]跳转下一个页面
     *
     * @param view          持有NavController的View
     * @param actionId      需要跳转的Action
     * @param args          传给Fragment的入参
     * @param popUp         true：弹出当前页面
     * @param finishStack   true:结束当前activity
     */
    fun navigate(view: View, actionId: Int, args: Bundle?, popUp: Boolean, finishStack: Boolean) {
        val navController = Navigation.findNavController(view)
        if (popUp) {
            //弹出然后跳转
            val navOptions = NavOptions.Builder().setPopUpTo(navController.graph.id, true).build()
            navController.navigate(actionId, args, navOptions)
        } else {
            //跳转
            navController.navigate(actionId, args)
        }
        if (finishStack && view.context is Activity) {
            //结束当前activity
            (view.context as Activity).finish()
        }
    }
}