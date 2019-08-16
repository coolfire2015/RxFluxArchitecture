package com.huyingbao.core.base.flux.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import com.huyingbao.core.arch.view.RxFluxDialog
import com.huyingbao.core.base.BaseActionCreator
import com.huyingbao.core.base.BaseView
import com.huyingbao.core.base.common.dialog.BaseCommonDialog
import javax.inject.Inject

/**
 * 使用Dagger.Android，持有ViewModel，自动管理订阅
 *
 * 布局文件值与实际显示一致
 *
 * Created by liujunfeng on 2019/1/1.
 */
abstract class BaseFluxDialog<T : ViewModel> :
        RxFluxDialog<T>(),
        BaseView {
    @Inject
    lateinit var baseActionCreator: BaseActionCreator

    /**
     * 注意此处android.R.id.content，配合[BaseCommonDialog.onStart]方法，使布局文件中背景值和尺寸值生效
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(getLayoutId(), dialog?.window?.findViewById(android.R.id.content), false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        afterCreate(savedInstanceState)
    }

    /**
     * Dialog对应的布局文件中背景值和尺寸值生效
     */
    override fun onStart() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        super.onStart()
    }
}