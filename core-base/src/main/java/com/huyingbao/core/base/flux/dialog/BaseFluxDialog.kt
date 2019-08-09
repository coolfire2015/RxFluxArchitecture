package com.huyingbao.core.base.flux.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import com.huyingbao.core.arch.view.RxFluxDialog
import com.huyingbao.core.base.BaseView
import com.huyingbao.core.common.action.CommonActionCreator
import javax.inject.Inject

/**
 * Created by liujunfeng on 2019/1/1.
 */
abstract class BaseFluxDialog<T : ViewModel> :
        RxFluxDialog<T>(),
        BaseView {
    @Inject
    lateinit var commonActionCreator: CommonActionCreator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        //注意此处android.R.id.content，配合onStart()方法，使布局文件中背景值和尺寸值生效
        return inflater.inflate(getLayoutId(), dialog?.window?.findViewById(android.R.id.content), false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // view创建之后的操作
        afterCreate(savedInstanceState)
    }

    /**
     * Dialog对应的布局文件中背景值和尺寸值生效
     */
    override fun onStart() {
        val window = dialog?.window
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        super.onStart()
    }
}