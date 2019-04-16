package com.huyingbao.core.base.rxview;

import android.content.Context;

import com.huyingbao.core.arch.store.RxActivityStore;
import com.huyingbao.core.arch.store.RxFragmentStore;
import com.huyingbao.core.arch.view.RxFluxView;
import com.huyingbao.core.base.view.BaseFragment;
import com.orhanobut.logger.Logger;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.AndroidSupportInjection;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public abstract class BaseRxFragment<T extends ViewModel> extends BaseFragment implements RxFluxView {
    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    private T mStore;

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
        if (tClass.getSuperclass() == RxActivityStore.class) {
            mStore = ViewModelProviders.of(getActivity(), mViewModelFactory).get(tClass);
        } else if (tClass.getSuperclass() == RxFragmentStore.class) {
            mStore = ViewModelProviders.of(this, mViewModelFactory).get(tClass);
        }
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
        try {
            AndroidSupportInjection.inject(this);
        } catch (IllegalArgumentException e) {
            Logger.e("依赖注入失败");
        }
        super.onAttach(context);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //View在destroy时,不再持有该Store对象
        mStore = null;
    }
}