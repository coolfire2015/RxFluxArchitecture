package com.huyingbao.core.common.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.huyingbao.core.base.dialog.BaseCommonDialog
import com.huyingbao.core.common.R
import com.huyingbao.core.common.module.CommonContants
import kotlinx.android.synthetic.main.common_dialog_info.*
import org.jetbrains.anko.toast
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 内容提交编辑框
 *
 * Created by liujunfeng on 2019/6/13.
 */
class CommonInfoDialog @Inject constructor() : BaseCommonDialog() {
    var info: Info? = null

    companion object {
        fun newInstance(): CommonInfoDialog {
            return CommonInfoDialog()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.common_dialog_info
    }

    override fun afterCreate(savedInstanceState: Bundle?) {

    }

    override fun onResume() {
        super.onResume()
        if (info == null) {
            context?.toast("请设置初始参数！")
            dismiss()
        } else {
            setTextView(info!!.title, tv_info_title)
            setTextView(info!!.content, tv_info_content)
            setTextView(info!!.editTitle, et_info_title)
            setTextView(info!!.editContent, et_info_content)
            tv_info_cancel.setOnClickListener { onCancelClicked() }
            tv_info_ok.setOnClickListener { onOkClicked() }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        info = null
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        info = null
    }

    /**
     * 设置需要显示的信息，
     *
     * @param info     如果info=""，依然显示
     * @param textView 需要展示的View
     */
    private fun setTextView(info: CharSequence?, textView: TextView?) {
        if (info == null) {
            textView!!.visibility = View.GONE
        } else {
            textView!!.text = info
            textView.visibility = View.VISIBLE
        }
    }

    /**
     * 取消
     */
    private fun onCancelClicked() {
        if (info != null && !TextUtils.isEmpty(info!!.actionSecond)) {
//            TODO
//             info!!.actionSecond?.let { mCommonActionCreator.postLocalAction(it) }
        }
        dismiss()
    }

    /**
     * 确定，输入框中的内容可以为空
     */
    private fun onOkClicked() {
        if (info == null) {
            dismiss()
        }
        val title = et_info_title!!.text.toString()
        val content = et_info_content!!.text.toString()
        if (info!!.infoDialogClickListener != null) {
            //如果有点击监听，则回调方法
            info!!.infoDialogClickListener!!.onConfirm(title, content)
        }
        if (!TextUtils.isEmpty(info!!.actionFirst)) {
            //TODO 如果设置Action，则发送Action
//            info!!.actionFirst?.let {
//                mCommonActionCreator.postLocalAction(it,
//                        CommonContants.Key.TITLE, title,
//                        CommonContants.Key.CONTENT, content)
//            }
        }
        dismiss()
    }

    /**
     * 点击回调接口
     */
    interface InfoDialogClickListener {
        /**
         * 点击确认
         */
        fun onConfirm(editTitle: String, editContent: String)
    }

    class Info {
        var title: CharSequence? = null
        var content: CharSequence? = null
        var editTitle: CharSequence? = null
        var editContent: CharSequence? = null
        var actionFirst: String? = null
        var actionSecond: String? = null
        var infoDialogClickListener: InfoDialogClickListener? = null
    }
}
