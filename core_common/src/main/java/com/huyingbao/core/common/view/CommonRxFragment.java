package com.huyingbao.core.common.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.arch.model.RxError;
import com.huyingbao.core.arch.view.RxFluxView;
import com.huyingbao.core.common.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.ParameterizedType;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public abstract class CommonRxFragment<T extends ViewModel> extends Fragment implements CommonView, RxFluxView, HasSupportFragmentInjector {
    @Inject
    protected ViewModelProvider.Factory mViewModelFactory;
    @Inject
    DispatchingAndroidInjector<Fragment> mChildFragmentInjector;

    private T mStore;

    private Unbinder mUnbinder;

    private boolean mBackAble;
    private CharSequence mTitle;

    private TextView mTvTop;
    private Toolbar mToolbarTop;
    private ActionBar mActionBarTop;

    protected boolean mIsVisibleToUser;

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return mChildFragmentInjector;
    }

    @Nullable
    @Override
    public T getRxStore() {
        if (mStore == null) {
            Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            mStore = ViewModelProviders.of(getActivity(), mViewModelFactory).get(tClass);
        }
        return mStore;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v("RxFlux", "6.1-onCreateView");
        //告诉FragmentManager:其管理的fragment应接收onCreateOptionsMenu(...)方法的调用指令.
        setHasOptionsMenu(true);// fragment中创建菜单
        initActionBar();//实例化宿主Activity中的ActionBar
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
            setTitle(mTitle, mBackAble);
        }
    }

    private void initActionBar() {
        View view = getActivity().getWindow().getDecorView();
        mToolbarTop = view.findViewById(R.id.tlb_top);
        mTvTop = view.findViewById(R.id.tv_top_title);
        if (mToolbarTop == null || !(getActivity() instanceof AppCompatActivity)) return;
        //取代原本的actionbar
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbarTop);
        //设置actionbar
        mActionBarTop = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (mActionBarTop == null) return;
        //不显示home图标
        mActionBarTop.setDisplayShowHomeEnabled(false);
        //不显示标题
        mActionBarTop.setDisplayShowTitleEnabled(false);
    }

    protected void setTitle(CharSequence title, boolean backAble) {
        mBackAble = backAble;
        mTitle = title;
        if (mTvTop == null) return;
        //设置标题
        mTvTop.setText(mTitle);
        if (mActionBarTop == null) return;
        //显示右侧返回图标
        mActionBarTop.setDisplayHomeAsUpEnabled(backAble);
        if (backAble) mActionBarTop.setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_material);
    }

    protected void setTitle(int titleId, boolean backAble) {
        setTitle(getText(titleId), backAble);
    }
}
