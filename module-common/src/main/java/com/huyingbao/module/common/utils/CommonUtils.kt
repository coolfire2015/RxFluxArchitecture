package com.huyingbao.module.common.utils

import android.app.Activity
import com.alibaba.android.arouter.launcher.ARouter
import com.huyingbao.core.arch.model.Error
import com.huyingbao.module.common.app.CommonAppConstants
import retrofit2.HttpException
import splitties.toast.toast
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * 公用显示错误信息
 */
fun showCommonError(activity: Activity, error: Error) {
    when (val throwable = error.throwable) {
        is HttpException -> {
            if (throwable.code() == 401) {
                //登录失效，结束当前页面，跳转登录页面
                activity.toast("登录失效！")
                activity.finish()
                ARouter.getInstance().build(CommonAppConstants.Router.LoginActivity)
                    .withBoolean(CommonAppConstants.Key.TO_LOGIN, true)
                    .navigation()
            } else {
                activity.toast("${throwable.code()}:${throwable.message()}")
            }
        }
        is SocketException -> activity.toast("网络异常！")
        is UnknownHostException -> activity.toast("网络异常！")
        is SocketTimeoutException -> activity.toast("连接超时！")
        else -> activity.toast(throwable.toString())
    }
}

///**
// * 公用显示错误重试
// */
//fun showCommonRetry(activity: Activity, rxRetry: RxRetry<*>) {
//    val coordinatorLayout = activity.findViewById<CoordinatorLayout>(R.id.cdl_content) ?: return
//    if (activity is BaseFluxActivity<*>) {
//        Snackbar.make(coordinatorLayout, rxRetry.tag, Snackbar.LENGTH_INDEFINITE)
//                .setAction("Retry") { activity.baseActionCreator.postRetryAction(rxRetry) }
//                .show()
//    }
//}

/**
 * 公用显示操作进度
 */
//fun showCommonLoading(activity: Activity, rxLoading: Loading) {
//    if (activity is AppCompatActivity) {
//        val fragmentByTag = activity.supportFragmentManager.findFragmentByTag(rxLoading.tag)
//        if (fragmentByTag == null && rxLoading.isLoading) {
//            //显示进度框
//            val commonLoadingDialog = CommonLoadingDialog.newInstance()
//            commonLoadingDialog.clickListener = object : CommonLoadingDialogClickListener {
//                override fun onCancel() {
////                    if (activity is BaseFluxActivity<*>) {
////                        TODO activity.baseActionCreator.removeAction(tag = rxLoading.tag)
////                        activity.toast("取消操作${rxLoading.tag}")
//                    }
//                }
//            }
//            commonLoadingDialog.show(activity.supportFragmentManager, rxLoading.tag)
//            return
//        }
//        if (fragmentByTag is CommonLoadingDialog && !rxLoading.isLoading) {
//            //隐藏进度框
//            fragmentByTag.dismiss()
//        }
//    }
//}

///**
// * 跳转网页展示页面[WebActivity]
// */
//fun Context.startWebActivity(url: String?, title: String?) {
//    startActivity(WebActivity.newIntent(this, url, title))
//}