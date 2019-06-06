package com.huyingbao.core.arch.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.huyingbao.core.arch.store.RxActivityStore;
import com.huyingbao.core.arch.utils.ClassUtils;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;


/**
 * Created by liujunfeng on 2019/1/1.
 */
public abstract class RxFluxActivity<T extends RxActivityStore> extends AppCompatActivity
        implements RxFluxView<T>, RxSubscriberView, HasAndroidInjector {
    private T mStore;
    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    @Inject
    DispatchingAndroidInjector<Object> mChildAndroidInjector;

    @Override
    public AndroidInjector<Object> androidInjector() {
        return mChildAndroidInjector;
    }

    @Nullable
    @Override
    public T getRxStore() {
        if (mStore != null) {
            return mStore;
        }
        Class<T> storeClass = ClassUtils.getGenericClass(getClass());
        if (storeClass == null) {
            throw new IllegalArgumentException("No generic class for Class<" + getClass().getCanonicalName() + ">");
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
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    /**
     * View在destroy时,不再持有该Store对象
     */
    @Override
    public void onDestroy() {
        mStore = null;
        super.onDestroy();
    }
}