package com.huyingbao.core.common.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.huyingbao.core.arch.action.RxActionManager;
import com.huyingbao.core.base.view.CommonDialog;
import com.huyingbao.core.common.R;
import com.huyingbao.core.common.R2;
import com.huyingbao.core.common.utils.CommonUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import butterknife.BindView;
import butterknife.OnClick;
import dagger.Lazy;

/**
 * 进度提示,通过依赖注入生成
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
public class CommonLoadingDialog extends CommonDialog {
    @Inject
    Lazy<RxActionManager> mRxActionManagerLazy;

    @BindView(R2.id.progress_bar_loading)
    ProgressBar mProgressBarLoading;
    @BindView(R2.id.tv_loading_notice)
    TextView mTvLoadingNotice;
    @BindView(R2.id.tv_loading_cancel)
    TextView mTvLoadingCancel;

    @StringRes
    private int mMessageInt;


    @Inject
    public CommonLoadingDialog() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.common_dialog_loading;
    }

    @Override
    public void afterCreate(@Nullable Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(false);
        if (mMessageInt == 0) {
            return;
        }
        String message = getString(mMessageInt);
        if (!TextUtils.isEmpty(message)) {
            mTvLoadingNotice.setText(message);
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        mMessageInt = 0;
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        mMessageInt = 0;
    }

    /**
     * 取消所有注册的订阅Action
     */
    @OnClick(R2.id.tv_loading_cancel)
    public void cancel() {
        mRxActionManagerLazy.get().clear();
        CommonUtils.showShortToast(getContext(), "取消操作！");
        dismiss();
    }

    /**
     * 设置进度弹框显示文字
     *
     * @param messageInt
     */
    public void setMessageInt(@StringRes int messageInt) {
        mMessageInt = messageInt;
    }
}
