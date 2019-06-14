package com.huyingbao.core.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.huyingbao.core.common.R;

/**
 * 自定义封装Info信息的CardView
 * <p>
 * Created by liujunfeng on 2019/6/12.
 */
public class CommonInfoCardView extends CardView {
    public CommonInfoCardView(Context context) {
        this(context, null);
    }

    public CommonInfoCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonInfoCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.common_cardview_info, this);
        setTypedArray(context, attrs);
    }

    /**
     * 1：布局文件中使用属性值直接设置
     */
    private void setTypedArray(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CommonInfoCardView);
        if (typedArray.hasValue(R.styleable.CommonInfoCardView_infoIcon)) {
            this.<ImageView>findViewById(R.id.infoIcon).setImageResource(typedArray.getResourceId(R.styleable.CommonInfoCardView_infoIcon, 0));
        }
        if (typedArray.hasValue(R.styleable.CommonInfoCardView_infoTitle)) {
            this.<TextView>findViewById(R.id.infoTitle).setText(typedArray.getText(R.styleable.CommonInfoCardView_infoTitle));
        }
        if (typedArray.hasValue(R.styleable.CommonInfoCardView_infoContent)) {
            this.<TextView>findViewById(R.id.infoContent).setText(typedArray.getText(R.styleable.CommonInfoCardView_infoContent));
        }
    }

    /**
     * 2：在代码中直接设置值
     * 3：在布局文件中使用DataBinding自动设置值
     */
    public void setInfoIcon(@DrawableRes int infoIcon) {
        this.<ImageView>findViewById(R.id.infoIcon).setImageResource(infoIcon);
    }

    public CharSequence getInfoTitle() {
        return this.<TextView>findViewById(R.id.infoTitle).getText();
    }

    public void setInfoTitle(@NonNull CharSequence infoTitle) {
        this.<TextView>findViewById(R.id.infoTitle).setText(infoTitle);
    }

    public CharSequence getInfoContent() {
        return this.<TextView>findViewById(R.id.infoContent).getText();
    }

    public void setInfoContent(@NonNull CharSequence infoContent) {
        this.<TextView>findViewById(R.id.infoContent).setText(infoContent);
    }
}
