package com.huyingbao.core.base.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.huyingbao.core.base.BaseView;
import com.huyingbao.core.common.action.CommonActionCreator;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 不使用Dagger.Android，不接收订阅回调
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public abstract class BaseCommonDialog extends AppCompatDialogFragment implements BaseView {
    @Inject
    protected CommonActionCreator mCommonActionCreator;

    private Unbinder mUnbinder;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //注意此处android.R.id.content
        return inflater.inflate(getLayoutId(), getDialog().getWindow().findViewById(android.R.id.content), false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //ButterKnife绑定
        mUnbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // view创建之后的操作
        afterCreate(savedInstanceState);
    }

    /**
     * Dialog对应的布局文件中背景值和尺寸值生效
     */
    @Override
    public void onStart() {
        final Window window = getDialog().getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        super.onStart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}