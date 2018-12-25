package com.huyingbao.core.common.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.arch.model.RxError;
import com.huyingbao.core.arch.store.RxStoreForActivity;
import com.huyingbao.core.arch.view.RxFluxFragment;
import com.huyingbao.core.arch.view.RxFluxView;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public abstract class CommonRxFragment<T extends RxStoreForActivity> extends RxFluxFragment implements CommonView, RxFluxView {
    protected boolean mIsVisibleToUser;
    private boolean mBackAble = true;
    private Unbinder mUnbinder;
    private String mTitle;

    @Nullable
    @Override
    public abstract T getRxStore();

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("RxFlux", "6.1-onCreateView");
        setHasOptionsMenu(true);// fragment中创建菜单
        View rootView = inflater.inflate(getLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        afterCreate(savedInstanceState);
        return rootView;
    }

    /**
     * 接收RxChange，粘性
     */
    @Override
    @Subscribe(sticky = true)
    public void onRxChanged(@NonNull RxChange rxChange) {
        Logger.e("1");
        EventBus.getDefault().removeStickyEvent(rxChange);
    }

    /**
     * 接收RxError，粘性
     * 该方法不经过store,
     * 由activity直接处理
     * rxflux中对错误的处理
     */
    @Override
    @Subscribe(sticky = true)
    public void onRxError(@NonNull RxError error) {
        EventBus.getDefault().removeStickyEvent(error);
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
        if (getActivity() instanceof CommonRxActivity)
            ((CommonRxActivity) getActivity()).initActionBar(title, backAble);
    }

    protected void initActionBar(String title) {
        mTitle = title;
        if (getActivity() instanceof CommonRxActivity)
            ((CommonRxActivity) getActivity()).initActionBar(title, mBackAble);
    }

    protected void initActionBar() {
        if (getActivity() instanceof CommonRxActivity)
            ((CommonRxActivity) getActivity()).initActionBar(mTitle, mBackAble);
    }
}
