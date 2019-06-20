package com.huyingbao.core.common.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import androidx.annotation.StringRes
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.base.dialog.BaseCommonDialog
import com.huyingbao.core.common.R
import kotlinx.android.synthetic.main.common_dialog_loading.*
import org.jetbrains.anko.toast
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 进度提示
 *
 * Created by liujunfeng on 2019/1/1.
 */
class CommonLoadingDialog @Inject constructor() : BaseCommonDialog() {
    @StringRes
    private var mMessageInt: Int = 0

    companion object {
        fun newInstance(): CommonLoadingDialog {
            return CommonLoadingDialog()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.common_dialog_loading
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        dialog!!.setCanceledOnTouchOutside(false)
        if (mMessageInt == 0) {
            return
        }
        val message = getString(mMessageInt)
        if (!TextUtils.isEmpty(message)) {
            tv_loading_notice!!.text = message
        }
        tv_loading_cancel.setOnClickListener { cancel() }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        mMessageInt = 0
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        mMessageInt = 0
    }

    /**
     * 取消订阅的Action
     */
    private fun cancel() {
        //TODO mRxActionManagerLazy.clear()
        context?.toast("取消操作！")
        dismiss()
    }

    /**
     * 设置进度弹框显示文字
     */
    fun setMessageInt(@StringRes messageInt: Int) {
        mMessageInt = messageInt
    }
}
