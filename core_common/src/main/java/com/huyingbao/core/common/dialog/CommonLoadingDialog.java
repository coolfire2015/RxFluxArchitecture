package com.huyingbao.core.common.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.huyingbao.core.arch.action.RxActionManager;
import com.huyingbao.core.arch.model.RxLoading;
import com.huyingbao.core.common.R;
import com.huyingbao.core.common.R2;
import com.huyingbao.core.common.util.CommonUtils;
import com.huyingbao.core.common.view.CommonDialogFragment;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 进度提示
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
public class CommonLoadingDialog extends CommonDialogFragment implements DialogInterface.OnShowListener {
    @Inject
    RxActionManager mRxActionManager;
    @Inject
    CommonUtils mCommonUtils;

    @BindView(R2.id.progress_bar_loading)
    ProgressBar mProgressBarLoading;
    @BindView(R2.id.tv_loading_notice)
    TextView mTvLoadingNotice;
    @BindView(R2.id.tv_loading_cancel)
    TextView mTvLoadingCancel;

    private CharSequence message;
    private int messageInt;
    private RxLoading mRxLoading;

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
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog == null) {
            return;
        }
        Window window = dialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = getResources().getDimensionPixelSize(R.dimen.dp_120);
        window.setAttributes(layoutParams);
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

    public void setRxLoading(RxLoading rxLoading) {
        mRxLoading = rxLoading;
    }

    @OnClick(R2.id.tv_loading_cancel)
    public void cancel() {
        mRxActionManager.clear();
        mCommonUtils.showShortToast(getContext(), "取消操作！");
        dismiss();
    }
}
