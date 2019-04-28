package com.huyingbao.core.arch.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.huyingbao.core.arch.store.RxActivityStore;
import com.huyingbao.core.arch.utils.ClassUtils;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;


/**
 * 实现三个接口
 * <p>
 * 1:RxFluxView:获取RxStore,并关联自身生命周期
 * <p>
 * 2:RxSubscriberView:交由 {@link com.huyingbao.core.arch.RxFlux} 根据其生命周期注册订阅或者取消订阅
 * <p>
 * 3:HasSupportFragmentInjector:依赖注入
 * <p>
 * Created by liujunfeng on 2019/1/1.
 *
 * @param <T>
 */
public abstract class RxFluxActivity<T extends RxActivityStore> extends AppCompatActivity
        implements RxFluxView<T>, RxSubscriberView, HasSupportFragmentInjector {
    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    @Inject
    DispatchingAndroidInjector<Fragment> mChildFragmentInjector;

    private T mStore;

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return mChildFragmentInjector;
    }

    @Nullable
    @Override
    public T getRxStore() {
        if (mStore != null) {
            return mStore;
        }
        Class<T> storeClass = ClassUtils.getGenericClass(getClass());
        if (storeClass == null) {
            return null;
        }
        mStore = ViewModelProviders.of(this, mViewModelFactory).get(storeClass);
        return mStore;
    }

    /**
     * 子类都需要在Module中使用dagger.android中的
     * {@link dagger.android.ContributesAndroidInjector}注解
     * 生成对应的注入器，在方法中进行依赖注入操作。
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //依赖注入
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //View在destroy时,不再持有该Store对象
        mStore = null;
    }
}