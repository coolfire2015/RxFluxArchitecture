package com.huyingbao.core.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.cardview.widget.CardView;

import com.huyingbao.core.common.R;

/**
 * 自定义封装Info信息的CardView
 * Created by liujunfeng on 2019/6/12.
 */
public class CommonInfoCardView extends CardView {
    @DrawableRes
    private int mInfoIcon;
    private String mInfoTitle;
    private String mInfoContent;

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

    @DrawableRes
    public int getInfoIcon() {
        return mInfoIcon;
    }

    /**
     * 2：在代码中直接设置值
     * 3：在布局文件中使用DataBinding自动设置值
     */
    public void setInfoIcon(@DrawableRes int infoIcon) {
        mInfoIcon = infoIcon;
        this.<ImageView>findViewById(R.id.infoIcon).setImageResource(mInfoIcon);
    }

    public String getInfoTitle() {
        return mInfoTitle;
    }

    public void setInfoTitle(String infoTitle) {
        mInfoTitle = infoTitle;
        this.<TextView>findViewById(R.id.infoTitle).setText(mInfoTitle);
    }

    public String getInfoContent() {
        return mInfoContent;
    }

    public void setInfoContent(String infoContent) {
        mInfoContent = infoContent;
        this.<TextView>findViewById(R.id.infoContent).setText(mInfoContent);
    }
}
