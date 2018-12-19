package com.huyingbao.core.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public abstract class CommonFragment extends RxFluxFragment implements CommonView {
    private boolean mBackAble = true;
    private boolean isVisibleToUser;
    private Unbinder mUnbinder;
    private String mTitle;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);// fragment中创建菜单
        View rootView = inflater.inflate(getLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        afterCreate(savedInstanceState);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

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
