package com.huyingbao.core.base.view;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.huyingbao.core.base.CommonView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public abstract class CommonDialogFragment extends AppCompatDialogFragment implements CommonView {
    private Unbinder mUnbinder;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //设置布局
        View rootView = LayoutInflater.from(getActivity()).inflate(getLayoutId(), null);
        //ButterKnife绑定
        mUnbinder = ButterKnife.bind(this, rootView);
        //创建AlertDialog
        AppCompatDialog dialog = new AppCompatDialog(getContext(), getTheme());
        dialog.setContentView(rootView);
        return dialog;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // view创建之后的操作
        afterCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
