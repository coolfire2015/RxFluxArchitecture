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
            CommonUtils.showShortToast(getContext(), "请设置初始参数");
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
    public void onCancleClicked() {
        if (mInfo != null && !TextUtils.isEmpty(mInfo.getActionSecond())) {
            mCommonActionCreator.postLocalAction(mInfo.getActionSecond());
        } else {
            dismiss();
        }
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
        } else {
            dismiss();
        }
    }

    public Info getInfo() {
        return mInfo;
    }

    public void setInfo(Info info) {
        mInfo = info;
    }

    public static class Info {
        private String title;
        private String content;
        private String editTitle;
        private String editContent;
        private String actionFirst;
        private String actionSecond;

        public Info() {

        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getEditTitle() {
            return editTitle;
        }

        public void setEditTitle(String editTitle) {
            this.editTitle = editTitle;
        }

        public String getEditContent() {
            return editContent;
        }

        public void setEditContent(String editContent) {
            this.editContent = editContent;
        }

        public String getActionFirst() {
            return actionFirst;
        }

        public void setActionFirst(String actionFirst) {
            this.actionFirst = actionFirst;
        }

        public String getActionSecond() {
            return actionSecond;
        }

        public void setActionSecond(String actionSecond) {
            this.actionSecond = actionSecond;
        }
    }
}
