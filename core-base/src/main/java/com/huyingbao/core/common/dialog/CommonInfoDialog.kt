package com.huyingbao.core.common.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.TextView
import com.huyingbao.core.base.R
import com.huyingbao.core.base.dialog.BaseCommonDialog
import com.huyingbao.core.common.module.CommonContants
import kotlinx.android.synthetic.main.common_dialog_info.*
import org.jetbrains.anko.toast

/**
 * 内容提交编辑框
 *
 * Created by liujunfeng on 2019/6/13.
 */
class CommonInfoDialog : BaseCommonDialog() {
    var clickListener: CommonInfoDialogClickListener? = null

    private var commonInfo: CommonInfo? = null

    companion object {
        fun newInstance(info: CommonInfo): CommonInfoDialog {
            return CommonInfoDialog().apply {
                arguments = Bundle().apply {
                    putParcelable(CommonContants.Key.INFO, info)
                }
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.common_dialog_info
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        arguments?.let {
            if (it.containsKey(CommonContants.Key.INFO)) {
                commonInfo = it.getParcelable(CommonContants.Key.INFO)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (commonInfo == null) {
            context?.toast("请设置初始参数！")
            dismiss()
        } else {
            setTextView(commonInfo!!.title, tv_info_title)
            setTextView(commonInfo!!.content, tv_info_content)
            setTextView(commonInfo!!.editTitle, et_info_title)
            setTextView(commonInfo!!.editContent, et_info_content)
            tv_info_cancel.setOnClickListener { dismiss() }
            tv_info_ok.setOnClickListener { onOkClicked() }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        commonInfo = null
        clickListener = null

    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        commonInfo = null
        clickListener = null
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
     * 确定，输入框中的内容可以为空
     */
    private fun onOkClicked() {
        val title = et_info_title!!.text.toString()
        val content = et_info_content!!.text.toString()
        clickListener?.onConfirm(title, content)
        dismiss()
    }
}

/**
 * 点击回调接口
 */
interface CommonInfoDialogClickListener {
    /**
     * 点击确认
     */
    fun onConfirm(editTitle: String, editContent: String)
}

/**
 * Dialog初始化信息封装
 */
class CommonInfo : Parcelable {
    var title: String? = null
    var content: String? = null
    var editTitle: String? = null
    var editContent: String? = null

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.title)
        dest.writeString(this.content)
        dest.writeString(this.editTitle)
        dest.writeString(this.editContent)
    }

    constructor() {}

    protected constructor(`in`: Parcel) {
        this.title = `in`.readString()
        this.content = `in`.readString()
        this.editTitle = `in`.readString()
        this.editContent = `in`.readString()
    }

    companion object {

        @JvmField
        val CREATOR: Parcelable.Creator<CommonInfo> = object : Parcelable.Creator<CommonInfo> {
            override fun createFromParcel(source: Parcel): CommonInfo {
                return CommonInfo(source)
            }

            override fun newArray(size: Int): Array<CommonInfo?> {
                return arrayOfNulls(size)
            }
        }
    }
}

