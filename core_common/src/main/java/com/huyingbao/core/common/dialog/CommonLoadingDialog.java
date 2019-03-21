package com.huyingbao.core.common.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.huyingbao.core.common.R;
import com.huyingbao.core.common.R2;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;

/**
 * 进度提示
 * Created by liujunfeng on 2017/1/1.
 */
@Singleton
public class CommonLoadingDialog extends CommonDialogFragment implements DialogInterface.OnShowListener {
    @BindView(R2.id.progress_bar_loading)
    ProgressBar mProgressBarLoading;
    @BindView(R2.id.tv_loading_notice)
    TextView mTvLoadingNotice;
    @BindView(R2.id.tv_loading_cancel)
    TextView mTvLoadingCancel;

    private CharSequence message;
    private int messageInt;

    @Inject
    public CommonLoadingDialog() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.common_dialog_loading;
    }

    @Override
    public void afterCreate(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onShow(DialogInterface dialog) {
        if (message != null && message.length() > 0) {
            mTvLoadingNotice.setText(message);
            mTvLoadingNotice.setVisibility(View.VISIBLE);
        } else if (messageInt > 0) {
            mTvLoadingNotice.setText(messageInt);
            mTvLoadingNotice.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
    }

    public void setMessage(CharSequence message) {
        this.message = message;
    }
}
