package com.huyingbao.core.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import com.huyingbao.core.base.BaseView

/**
 * 不使用Dagger.Android，不持有ViewModel，不自动管理订阅
 *
 * 布局文件值与实际显示一致
 *
 * Created by liujunfeng on 2019/1/1.
 */
abstract class BaseDialog() :
    AppCompatDialogFragment(),
    BaseView {
    /**
     * 注意此处android.R.id.content，配合[BaseDialog.onStart]方法，使布局文件中背景值和尺寸值生效
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            getLayoutId(),
            dialog?.window?.findViewById(android.R.id.content),
            false
        )
    }

    /**
     * DialogFragment内存泄露解决方案
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        if (showsDialog) {
            showsDialog = false
        }
        super.onActivityCreated(savedInstanceState)
        showsDialog = true

        val view = view
        if (view != null) {
            if (view.parent != null) {
                throw IllegalStateException("DialogFragment can not be attached to a container view")
            }
            dialog!!.setContentView(view)
        }
        val activity = activity
        if (activity != null) {
            dialog!!.setOwnerActivity(activity)
        }
        if (savedInstanceState != null) {
            val dialogState = savedInstanceState.getBundle("android:savedDialogState")
            if (dialogState != null) {
                dialog!!.onRestoreInstanceState(dialogState)
            }
        }
        afterCreate(savedInstanceState)
    }

    /**
     * Dialog对应的布局文件中背景值和尺寸值生效
     */
    override fun onStart() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        super.onStart()
    }
}