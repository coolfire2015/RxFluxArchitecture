package com.huyingbao.core.base.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huyingbao.core.base.BaseView;
import com.huyingbao.core.common.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public abstract class BaseFragment extends Fragment implements BaseView {
    private Unbinder mUnbinder;

    private boolean mBackAble;
    private CharSequence mTitle;

    protected TextView mTvTop;
    private Toolbar mToolbarTop;
    private ActionBar mActionBarTop;

    protected boolean mIsVisibleToUser;

    @Override
    public void onAttach(Context context) {
        //告诉FragmentManager:其管理的fragment应接收onCreateOptionsMenu(...)方法的调用指令.
        //fragment中创建菜单
        setHasOptionsMenu(true);
        super.onAttach(context);
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
        //实例化宿主Activity中的ActionBar
        initActionBar();
        // view创建之后的操作
        afterCreate(savedInstanceState);
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
        //当前页面显示时，显示对应的标题
        if (!hidden) {
            setTitle(mTitle, mBackAble);
        }
    }

    /**
     * 实例化宿主Activity中的ActionBar
     */
    private void initActionBar() {
        View view = getActivity().getWindow().getDecorView();
        mToolbarTop = view.findViewById(R.id.tlb_top);
        mTvTop = view.findViewById(R.id.tv_top_title);
        if (mToolbarTop == null || !(getActivity() instanceof AppCompatActivity)) {
            return;
        }
        //取代原本的actionbar
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbarTop);
        //设置actionbar
        mActionBarTop = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (mActionBarTop == null) {
            return;
        }
        //不显示home图标
        mActionBarTop.setDisplayShowHomeEnabled(false);
        //不显示标题
        mActionBarTop.setDisplayShowTitleEnabled(false);
    }

    /**
     * 设置Toolbar
     *
     * @param title    Toolbar标题
     * @param backAble true：显示返回箭头，false：不显示
     */
    protected void setTitle(CharSequence title, boolean backAble) {
        mBackAble = backAble;
        mTitle = title;
        if (mTvTop == null) {
            return;
        }
        //设置标题
        mTvTop.setText(mTitle);
        if (mActionBarTop == null) {
            return;
        }
        //显示右侧返回图标
        mActionBarTop.setDisplayHomeAsUpEnabled(backAble);
        if (backAble) {
            mActionBarTop.setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_material);
        }
    }

    /**
     * 设置Toolbar
     *
     * @param titleId  Toolbar标题
     * @param backAble true：显示返回箭头，false：不显示
     */
    protected void setTitle(int titleId, boolean backAble) {
        setTitle(getText(titleId), backAble);
    }
}
