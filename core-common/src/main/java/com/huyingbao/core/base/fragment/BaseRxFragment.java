package com.huyingbao.core.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

import com.huyingbao.core.arch.view.RxFluxFragment;
import com.huyingbao.core.base.BaseView;
import com.huyingbao.core.common.R;
import com.huyingbao.core.common.action.CommonActionCreator;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public abstract class BaseRxFragment<T extends ViewModel> extends RxFluxFragment<T> implements BaseView {
    @Inject
    protected CommonActionCreator mCommonActionCreator;

    private Unbinder mUnbinder;

    private boolean mBackAble;
    private CharSequence mTitle;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //告诉FragmentManager:其管理的fragment应接收onCreateOptionsMenu(...)方法的调用指令.
        //fragment中创建菜单
        setHasOptionsMenu(true);
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //ButterKnife绑定
        mUnbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // view创建之后的操作
        afterCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
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
        //当前页面显示时，显示对应的标题
        if (!hidden) {
            setTitle(mTitle, mBackAble);
        }
    }

    /**
     * 设置标题，设置返回图标
     *
     * @param title    Toolbar标题
     * @param backAble true：Home按钮作为返回箭头，false：默认设置
     */
    protected void setTitle(CharSequence title, boolean backAble) {
        if (getActivity() == null) {
            return;
        }
        mBackAble = backAble;
        mTitle = title;
        ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (!(getActivity() instanceof AppCompatActivity) || supportActionBar == null) {
            //设置标题
            getActivity().setTitle(mTitle);
            return;
        }
        //显示标题
        supportActionBar.setDisplayShowTitleEnabled(true);
        //设置标题
        supportActionBar.setTitle(mTitle);
        //显示右侧返回图标
        if (backAble) {
            //显示Home按钮
            supportActionBar.setDisplayShowHomeEnabled(true);
            //设置Home按钮作为返回按钮
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            //设置Home按钮图标
            supportActionBar.setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_material);
        }
    }

    /**
     * 设置标题，设置返回图标
     *
     * @param titleId    Toolbar标题
     * @param backAble true：Home按钮作为返回箭头，false：默认设置
     */
    protected void setTitle(int titleId, boolean backAble) {
        setTitle(getText(titleId), backAble);
    }
}