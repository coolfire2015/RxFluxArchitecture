//package com.huyingbao.module.common.ui.update.view
//
//import android.os.Bundle
//import com.huyingbao.core.arch.view.SubscriberView
//import com.huyingbao.core.base.common.dialog.BaseCommonDialog
//import com.huyingbao.core.progress.RxProgress
//import com.huyingbao.module.common.R
//import com.huyingbao.module.common.app.CommonAppConstants
//import com.huyingbao.module.common.ui.update.action.CommonUpdateActionCreator
//import com.huyingbao.module.common.ui.update.action.getExternalCacheDir
//import com.huyingbao.module.common.ui.update.model.AppUpdateState
//import dagger.hilt.android.AndroidEntryPoint
//import kotlinx.android.synthetic.main.common_dialog_update.*
//import org.greenrobot.eventbus.Subscribe
//import org.greenrobot.eventbus.ThreadMode
//import splitties.toast.toast
//import javax.inject.Inject
//
///**
// * 通用更新页面
// *
// * Created by liujunfeng on 2019/8/27.
// */
//@AndroidEntryPoint
//class CommonUpdateDialog : BaseCommonDialog(), SubscriberView {
//    @Inject
//    lateinit var commonUpdateActionCreator: CommonUpdateActionCreator
//    private var appUpdateState: AppUpdateState = AppUpdateState.LATEST
//    private var apkUrl: String? = null
//    private var updateLog: String? = null
//    private var archiveFilepath: String? = null
//
//    companion object {
//        const val TAG = "CommonUpdateDialog"
//
//        /**
//         * @param apkUrl            下载的地址
//         * @param archiveFilepath   已经下载在本机的存储路径
//         * @param changelog         更新日志
//         * @param appUpdateState    需要操作的App状态
//         */
//        fun newInstance(apkUrl: String? = null,
//                        changelog: String? = null,
//                        archiveFilepath: String? = null,
//                        appUpdateState: AppUpdateState
//        ): CommonUpdateDialog {
//            return CommonUpdateDialog().apply {
//                arguments = Bundle().apply {
//                    //枚举的序数作为参数传入
//                    putInt(CommonAppConstants.Key.INDEX, appUpdateState.ordinal)
//                    putString(CommonAppConstants.Key.URL, apkUrl)
//                    putString(CommonAppConstants.Key.CONTENT, changelog)
//                    putString(CommonAppConstants.Key.FILE_PATH, archiveFilepath)
//                }
//            }
//        }
//    }
//
//    override fun getLayoutId() = R.layout.common_dialog_update
//
//    override fun afterCreate(savedInstanceState: Bundle?) {
//        initView()
//        setOnClickCancel()
//        setOnClickOk()
//    }
//
//    /**
//     * 初始化界面
//     */
//    private fun initView() {
//        arguments?.let {
//            appUpdateState = AppUpdateState.values()[it.getInt(CommonAppConstants.Key.INDEX, 0)]
//            apkUrl = it.getString(CommonAppConstants.Key.URL)
//            updateLog = it.getString(CommonAppConstants.Key.CONTENT)
//            archiveFilepath = it.getString(CommonAppConstants.Key.FILE_PATH)
//        }
//        when (appUpdateState) {
//            AppUpdateState.INSTALL -> tv_update_title.text = "安装"
//            AppUpdateState.UPDATE -> tv_update_title.text = "更新"
//            AppUpdateState.DOWNLOAD -> tv_update_title.text = "下载"
//            AppUpdateState.LATEST -> tv_update_title.text = "最新"
//        }
//        tv_update_content.text = updateLog
//    }
//
//    /**
//     * 点击取消按钮
//     */
//    private fun setOnClickCancel() {
//        tv_update_cancel.setOnClickListener { dismiss() }
//    }
//
//    /**
//     * 点击确定按钮
//     */
//    private fun setOnClickOk() {
//        tv_update_ok.setOnClickListener {
//            when (appUpdateState) {
//                AppUpdateState.LATEST -> context?.toast("最新")
//                AppUpdateState.INSTALL -> context?.toast("安装")
//                AppUpdateState.DOWNLOAD, AppUpdateState.UPDATE -> {
//                    apkUrl?.let {
//                        commonUpdateActionCreator.downloadStart(
//                                tag = TAG,
//                                url = it,
//                                local = "${getExternalCacheDir(requireContext(), "app")}/asd.apk")
//                    }
//                }
//            }
//        }
//    }
//
//    @Subscribe(tags = [TAG], sticky = true, threadMode = ThreadMode.MAIN)
//    fun onProgress(progressInfo: RxProgress?) {
//        if (progressInfo == null || tv_update_content == null) return
//        tv_update_content.text = "${progressInfo.percent}%"
//        if (progressInfo.percent == 100) {
//            if (isResumed) {
//                //当前正在显示
//                dismiss()
//            }
//        }
//    }
//}