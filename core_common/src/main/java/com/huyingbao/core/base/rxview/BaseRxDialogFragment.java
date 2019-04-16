package com.huyingbao.core.base.rxview;

import android.content.Context;

import com.huyingbao.core.arch.store.RxActivityStore;
import com.huyingbao.core.arch.store.RxFragmentStore;
import com.huyingbao.core.arch.view.RxFluxView;
import com.huyingbao.core.base.view.BaseDialogFragment;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public abstract class BaseRxDialogFragment<T extends ViewModel> extends BaseDialogFragment implements RxFluxView, HasSupportFragmentInjector {
    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    @Inject
    DispatchingAndroidInjector<Fragment> childFragmentInjector;

    private T mStore;

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return childFragmentInjector;
    }

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
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //View在destroy时,不再持有该Store对象
        mStore = null;
    }
}