package com.huyingbao.core.common.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.arch.model.RxError;
import com.huyingbao.core.arch.store.RxActionDispatch;
import com.huyingbao.core.arch.view.RxFluxFragment;
import com.huyingbao.core.arch.view.RxFluxView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public abstract class CommonRxFragment<T extends RxActionDispatch> extends RxFluxFragment implements CommonView, RxFluxView {
    protected boolean mIsVisibleToUser;
    private Unbinder mUnbinder;
    private CharSequence mTitle;

    @Nullable
    @Override
    public abstract T getRxStore();

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v("RxFlux", "6.1-onCreateView");
        //告诉FragmentManager:其管理的fragment应接收onCreateOptionsMenu(...)方法的调用指令.
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
        //收到后，移除粘性通知
        EventBus.getDefault().removeStickyEvent(rxChange);
    }

    /**
     * 接收RxError，粘性
     * 该方法不经过store,
     * 由fragment直接处理
     */
    @Override
    @Subscribe(sticky = true)
    public void onRxError(@NonNull RxError error) {
        //收到后，移除粘性通知
        EventBus.getDefault().removeStickyEvent(error);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    /**
     * 可见状态转变
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.mIsVisibleToUser = isVisibleToUser;
    }

    /**
     * 隐藏状态转变
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        //从隐藏转为非隐藏的时候调用
        if (!hidden) {//当前页面显示时，显示对应的标题
            getActivity().setTitle(mTitle);
        }
    }

    /**
     * 设置页面标题
     *
     * @param title
     */
    protected void setTitle(CharSequence title) {
        mTitle = title;
        getActivity().setTitle(mTitle);
    }

    protected void setTitle(int titleId) {
        setTitle(getText(titleId));
    }
}
