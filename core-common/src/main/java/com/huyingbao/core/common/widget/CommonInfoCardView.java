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
 * 自定义封装Info信息的Layout
 */
public class CommonInfoCardView extends CardView {
    @DrawableRes
    private int infoIcon;
    private String infoTitle;
    private String infoContent;

    public CommonInfoCardView(Context context) {
        this(context, null);
    }

    public CommonInfoCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonInfoCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.common_cardview_info, this);
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

    public int getInfoIcon() {
        return infoIcon;
    }

    public void setInfoIcon(@DrawableRes int infoIcon) {
        this.infoIcon = infoIcon;
        this.<ImageView>findViewById(R.id.infoIcon).setImageResource(infoIcon);
    }

    public String getInfoTitle() {
        return infoTitle;
    }

    public void setInfoTitle(String infoTitle) {
        this.infoTitle = infoTitle;
        this.<TextView>findViewById(R.id.infoTitle).setText(infoTitle);
    }

    public String getInfoContent() {
        return infoContent;
    }

    public void setInfoContent(String infoContent) {
        this.infoContent = infoContent;
        this.<TextView>findViewById(R.id.infoContent).setText(infoContent);
    }
}
