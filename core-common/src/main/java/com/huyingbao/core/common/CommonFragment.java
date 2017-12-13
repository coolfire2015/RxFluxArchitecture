package com.huyingbao.core.common;

import com.huyingbao.core.view.BaseFragment;
import com.huyingbao.core.view.BaseView;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public abstract class CommonFragment extends BaseFragment implements BaseView {
    private String mTitle;
    private boolean isVisibleToUser;
    private boolean mBackAble = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        //从隐藏转为非隐藏的时候调用
        if (!hidden) initActionBar();
    }

    protected void initActionBar(String title, boolean backAble) {
        mTitle = title;
        mBackAble = backAble;
        if (mContext instanceof CommonToolbarActivity)
            ((CommonToolbarActivity) mContext).initActionBar(title, backAble);
    }

    protected void initActionBar(String title) {
        mTitle = title;
        if (mContext instanceof CommonToolbarActivity)
            ((CommonToolbarActivity) mContext).initActionBar(title, mBackAble);
    }

    protected void initActionBar() {
        if (mContext instanceof CommonToolbarActivity)
            ((CommonToolbarActivity) mContext).initActionBar(mTitle, mBackAble);
    }
}
