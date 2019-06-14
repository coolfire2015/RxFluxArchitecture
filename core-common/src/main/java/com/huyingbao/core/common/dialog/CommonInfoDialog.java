package com.huyingbao.core.common.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huyingbao.core.base.dialog.BaseCommonDialog;
import com.huyingbao.core.common.R;
import com.huyingbao.core.common.R2;
import com.huyingbao.core.common.module.CommonContants;
import com.huyingbao.core.common.utils.CommonUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 内容提交编辑框
 * <p>
 * Created by liujunfeng on 2019/6/13.
 */
@Singleton
public class CommonInfoDialog extends BaseCommonDialog {
    @BindView(R2.id.tv_info_title)
    TextView mTvInfoTitle;
    @BindView(R2.id.tv_info_content)
    TextView mTvInfoContent;
    @BindView(R2.id.et_info_title)
    EditText mEtInfoTitle;
    @BindView(R2.id.et_info_content)
    EditText mEtInfoContent;
    @BindView(R2.id.tv_info_cancel)
    TextView mTvInfoCancel;
    @BindView(R2.id.tv_info_ok)
    TextView mTvInfoOk;
    private Info mInfo;

    @Inject
    public CommonInfoDialog() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.common_dialog_info;
    }

    @Override
    public void afterCreate(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mInfo == null) {
            CommonUtils.showShortToast(getContext(), "请设置初始参数！");
            dismiss();
        } else {
            setTextView(mInfo.getTitle(), mTvInfoTitle);
            setTextView(mInfo.getContent(), mTvInfoContent);
            setTextView(mInfo.getEditTitle(), mEtInfoTitle);
            setTextView(mInfo.getEditContent(), mEtInfoContent);
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        mInfo = null;
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        mInfo = null;
    }

    /**
     * 设置需要显示的信息
     */
    private void setTextView(String title, TextView tvInfoTitle) {
        if (TextUtils.isEmpty(title)) {
            tvInfoTitle.setVisibility(View.GONE);
        } else {
            tvInfoTitle.setText(title);
            tvInfoTitle.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 取消
     */
    @OnClick(R2.id.tv_info_cancel)
    public void onCancelClicked() {
        if (mInfo != null && !TextUtils.isEmpty(mInfo.getActionSecond())) {
            mCommonActionCreator.postLocalAction(mInfo.getActionSecond());
        }
        dismiss();
    }

    /**
     * 确定
     */
    @OnClick(R2.id.tv_info_ok)
    public void onOkClicked() {
        if (mInfo != null && !TextUtils.isEmpty(mInfo.getActionFirst())) {
            String title = mEtInfoTitle.getText().toString();
            String content = mEtInfoContent.getText().toString();
            mCommonActionCreator.postLocalAction(mInfo.getActionFirst(),
                    CommonContants.Key.TITLE, title,
                    CommonContants.Key.CONTENT, content);
        }
        dismiss();
    }

    public Info getInfo() {
        return mInfo;
    }

    public void setInfo(Info info) {
        mInfo = info;
    }

    public static class Info {
        private String mTitle;
        private String mContent;
        private String mEditTitle;
        private String mEditContent;
        private String mActionFirst;
        private String mActionSecond;

        public Info() {

        }

        public String getTitle() {
            return mTitle;
        }

        public void setTitle(String title) {
            this.mTitle = title;
        }

        public String getContent() {
            return mContent;
        }

        public void setContent(String content) {
            this.mContent = content;
        }

        public String getEditTitle() {
            return mEditTitle;
        }

        public void setEditTitle(String editTitle) {
            this.mEditTitle = editTitle;
        }

        public String getEditContent() {
            return mEditContent;
        }

        public void setEditContent(String editContent) {
            this.mEditContent = editContent;
        }

        public String getActionFirst() {
            return mActionFirst;
        }

        public void setActionFirst(String actionFirst) {
            this.mActionFirst = actionFirst;
        }

        public String getActionSecond() {
            return mActionSecond;
        }

        public void setActionSecond(String actionSecond) {
            this.mActionSecond = actionSecond;
        }
    }
}
