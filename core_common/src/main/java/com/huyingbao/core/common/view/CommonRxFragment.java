package com.huyingbao.core.common.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.arch.model.RxError;
import com.huyingbao.core.arch.model.RxLoading;
import com.huyingbao.core.arch.view.RxFluxView;
import com.huyingbao.core.common.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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
import dagger.android.support.AndroidSupportInjection;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public abstract class CommonRxFragment<T extends ViewModel> extends Fragment implements CommonView, RxFluxView {
    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    private T mStore;
    private Unbinder mUnbinder;

    private boolean mBackAble;
    private CharSequence mTitle;

    private TextView mTvTop;
    private Toolbar mToolbarTop;
    private ActionBar mActionBarTop;

    protected boolean mIsVisibleToUser;

    @Nullable
    @Override
    public T getRxStore() {
        if (mStore != null) {
            return mStore;
        }
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (!(genericSuperclass instanceof ParameterizedType)) {
            return null;
        }
        Class<T> tClass = (Class<T>) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        mStore = ViewModelProviders.of(getActivity(), mViewModelFactory).get(tClass);
        return mStore;
    }

    /**
     * 子类都需要在Module中使用dagger.android中的
     * {@link dagger.android.ContributesAndroidInjector}注解
     * 生成对应的注入器，在方法中进行依赖注入操作。
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        //依赖注入
        AndroidSupportInjection.inject(this);
        //告诉FragmentManager:其管理的fragment应接收onCreateOptionsMenu(...)方法的调用指令.
        //fragment中创建菜单
        setHasOptionsMenu(true);
        //实例化宿主Activity中的ActionBar
        initActionBar();
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
        // view创建之后的操作
        afterCreate(savedInstanceState);
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
    public void onRxError(@NonNull RxError rxError) {
        //收到后，移除粘性通知
        EventBus.getDefault().removeStickyEvent(rxError);
    }

    /**
     * 接收RxError，粘性
     * 该方法不经过store,
     * 由fragment直接处理
     */
    @Override
    @Subscribe(sticky = true)
    public void onRxLoading(@NonNull RxLoading rxLoading) {
        //收到后，移除粘性通知
        EventBus.getDefault().removeStickyEvent(rxLoading);
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
