package com.huyingbao.core.common.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import androidx.annotation.StringRes
import com.huyingbao.core.base.dialog.BaseCommonDialog
import com.huyingbao.core.common.R
import kotlinx.android.synthetic.main.common_dialog_loading.*

/**
 * 进度提示
 *
 * Created by liujunfeng on 2019/1/1.
 */
class CommonLoadingDialog : BaseCommonDialog() {
    var clickListener: CommonLoadingDialogClickListener? = null

    @StringRes
    private var messageInt: Int = 0

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
        if (messageInt != 0) {
            val message = getString(messageInt)
            if (!TextUtils.isEmpty(message)) {
                tv_loading_notice!!.text = message
            }
        }
        tv_loading_cancel.setOnClickListener {
            clickListener?.onCancel()
            dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        messageInt = 0
        clickListener = null
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        messageInt = 0
        clickListener = null
    }

    /**
     * 设置进度弹框显示文字
     */
    fun setMessageInt(@StringRes messageInt: Int) {
        this.messageInt = messageInt
    }
}

/**
 * 点击回调接口
 */
interface CommonLoadingDialogClickListener {
    /**
     * 点击取消
     */
    fun onCancel()
}
