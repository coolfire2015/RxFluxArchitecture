package com.huyingbao.core.common;

import com.huyingbao.core.RxFluxView;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public abstract class CommonFragment extends RxFluxFragment implements RxFluxView {
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
        if (getActivity() instanceof CommonActivity)
            ((CommonActivity) getActivity()).initActionBar(title, backAble);
    }

    protected void initActionBar(String title) {
        mTitle = title;
        if (getActivity() instanceof CommonActivity)
            ((CommonActivity) getActivity()).initActionBar(title, mBackAble);
    }

    protected void initActionBar() {
        if (getActivity() instanceof CommonActivity)
            ((CommonActivity) getActivity()).initActionBar(mTitle, mBackAble);
    }
}
