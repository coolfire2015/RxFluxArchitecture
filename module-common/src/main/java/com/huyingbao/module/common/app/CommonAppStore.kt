package com.huyingbao.module.common.app

import android.app.Application
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.store.RxAppStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommonAppStore @Inject constructor(
        application: Application,
        rxDispatcher: RxDispatcher
) : RxAppStore(application, rxDispatcher) {
//    /**
//     * 接收[RxError]，粘性
//     * 该方法不经过RxStore,
//     * 由RxFluxView直接处理
//     */
//    @Subscribe(sticky = true)
//    fun onRxError(rxError: RxError) {
//        when (val throwable = rxError.throwable) {
//            is HttpException -> application.toast("${throwable.code()}:${throwable.message()}")
//            is SocketException -> application.toast("网络异常!")
//            is UnknownHostException -> application.toast("网络异常!")
//            is SocketTimeoutException -> application.toast("连接超时!")
//            else -> application.toast(throwable.toString())
//        }
//    }
//    /**
//     * 接收[RxRetry]，粘性
//     * 该方法不经过RxStore,
//     * 由RxFluxView直接处理
//     */
//    @Subscribe(sticky = true)
//    fun onRxRetry(rxRetry: RxRetry<*>) {
//        val coordinatorLayout = findViewById<CoordinatorLayout>(R.id.cdl_content) ?: return
//        Snackbar.make(coordinatorLayout, rxRetry.tag, Snackbar.LENGTH_INDEFINITE)
//                .setAction("Retry") { commonActionCreator.postRetryAction(rxRetry) }
//                .show()
//    }
//
//    /**
//     * 显示进度对话框
//     * 接收[RxLoading]，粘性
//     * 该方法不经过RxStore,
//     * 由RxFluxView直接处理
//     */
//    @Subscribe(sticky = true)
//    fun onRxLoading(rxLoading: RxLoading) {
//        val fragmentByTag = supportFragmentManager.findFragmentByTag(rxLoading.tag)
//        if (fragmentByTag == null && rxLoading.isLoading) {
//            //显示进度框
//            val commonLoadingDialog = CommonLoadingDialog.newInstance()
//            commonLoadingDialog.clickListener = object : CommonLoadingDialogClickListener {
//                override fun onCancel() {
//                    commonActionCreator.removeRxAction(tag = rxLoading.tag)
//                    toast("取消操作${rxLoading.tag}")
//                }
//            }
//            commonLoadingDialog.show(supportFragmentManager, rxLoading.tag)
//            return
//        }
//        if (fragmentByTag is CommonLoadingDialog && !rxLoading.isLoading) {
//            //隐藏进度框
//            fragmentByTag.dismiss()
//        }
//    }
}
