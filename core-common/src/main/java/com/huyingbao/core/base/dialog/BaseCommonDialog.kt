package com.huyingbao.core.base.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import com.huyingbao.core.base.BaseView

/**
 * 不使用Dagger.Android，不接收订阅回调
 *
 * Created by liujunfeng on 2019/1/1.
 */
abstract class BaseCommonDialog : AppCompatDialogFragment(), BaseView {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        //注意此处android.R.id.content
        return inflater.inflate(getLayoutId(), dialog!!.window!!.findViewById(android.R.id.content), false)
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
        val window = dialog!!.window
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        super.onStart()
    }
}