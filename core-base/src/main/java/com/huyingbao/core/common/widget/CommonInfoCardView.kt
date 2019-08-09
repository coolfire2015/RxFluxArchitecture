package com.huyingbao.core.common.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import com.huyingbao.core.base.R
import kotlinx.android.synthetic.main.common_cardview_info.view.*

/**
 * 自定义封装Info信息的CardView
 *
 *
 * Created by liujunfeng on 2019/6/12.
 */
class CommonInfoCardView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0)
    : CardView(context, attrs, defStyleAttr) {

    var infoTitle: CharSequence? = null
        set(value) {
            value?.let { tv_info_title.text = it }
            field = value
        }

    var infoContent: CharSequence? = null
        set(value) {
            value?.let { tv_info_content.text = it }
            field = value
        }

    var infoIcon: Int? = null
        set(value) {
            value?.let { iv_info_icon.setImageResource(it) }
            field = value
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.common_cardview_info, this)
        setTypedArray(context, attrs)
    }

    /**
     * 1：布局文件中使用属性值直接设置
     */
    private fun setTypedArray(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CommonInfoCardView)
        if (typedArray.hasValue(R.styleable.CommonInfoCardView_infoIcon)) {
            infoIcon = typedArray.getResourceId(R.styleable.CommonInfoCardView_infoIcon, 0)
        }
        if (typedArray.hasValue(R.styleable.CommonInfoCardView_infoTitle)) {
            infoTitle = typedArray.getText(R.styleable.CommonInfoCardView_infoTitle)
        }
        if (typedArray.hasValue(R.styleable.CommonInfoCardView_infoContent)) {
            infoContent = typedArray.getText(R.styleable.CommonInfoCardView_infoContent)
        }
    }
}
