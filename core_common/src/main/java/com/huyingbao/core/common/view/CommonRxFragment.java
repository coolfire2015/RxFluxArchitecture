package com.huyingbao.core.common.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huyingbao.core.arch.store.RxStore;
import com.huyingbao.core.arch.view.RxFluxFragment;
import com.huyingbao.core.arch.view.RxFluxView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public abstract class CommonRxFragment<T extends RxStore> extends RxFluxFragment implements CommonView,RxFluxView {
    protected boolean mIsVisibleToUser;
    private boolean mBackAble = true;
    private Unbinder mUnbinder;
    private String mTitle;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("RxFlux","6.1-onCreateView");
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
        this.mIsVisibleToUser = isVisibleToUser;
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
        if (getActivity() instanceof CommonFxActivity)
            ((CommonFxActivity) getActivity()).initActionBar(title, backAble);
    }

    protected void initActionBar(String title) {
        mTitle = title;
        if (getActivity() instanceof CommonFxActivity)
            ((CommonFxActivity) getActivity()).initActionBar(title, mBackAble);
    }

    protected void initActionBar() {
        if (getActivity() instanceof CommonFxActivity)
            ((CommonFxActivity) getActivity()).initActionBar(mTitle, mBackAble);
    }

    @Nullable
    @Override
    public abstract T getRxStore();
}
