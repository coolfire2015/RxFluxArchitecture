package com.huyingbao.core.view;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatDialogFragment;

public abstract class BaseDialogFragment extends DaggerAppCompatDialogFragment implements BaseView {
    @Inject
    protected ViewModelProvider.Factory mViewModelFactory;
    protected Context mContext;
    private Unbinder mUnbinder;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mContext = getActivity();
        View rootView = LayoutInflater.from(mContext).inflate(getLayoutId(), null);
        mUnbinder = ButterKnife.bind(this, rootView);
        afterCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(rootView);
        return builder.create();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
