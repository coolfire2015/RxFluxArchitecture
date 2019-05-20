package com.huyingbao.core.arch.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.huyingbao.core.arch.RxFlux;
import com.huyingbao.core.arch.store.RxActivityStore;
import com.huyingbao.core.arch.store.RxFragmentStore;
import com.huyingbao.core.arch.utils.ClassUtils;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * Created by liujunfeng on 2019/1/1.
 *
 * @param <T>
 */
public abstract class RxFluxDialog<T extends ViewModel> extends AppCompatDialogFragment
        implements RxFluxView<T>, RxSubscriberView {
    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    private T mStore;

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
        if (storeClass.getSuperclass() == RxActivityStore.class) {
            mStore = ViewModelProviders.of(getActivity(), mViewModelFactory).get(storeClass);
        } else if (storeClass.getSuperclass() == RxFragmentStore.class) {
            mStore = ViewModelProviders.of(this, mViewModelFactory).get(storeClass);
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
        if (ClassUtils.getGenericClass(getClass()) != null) {
            //如果持有Store,需要子类在Module中使用dagger.android实现依赖注入操作
            AndroidSupportInjection.inject(this);
        } else {
            //未使用dagger.android
            Log.w(RxFlux.TAG, "Not use dagger.android in " + getClass().getSimpleName());
        }
        super.onAttach(context);
    }

    /**
     * Dialog对应的布局文件中背景值和尺寸值生效
     */
    @Override
    public void onStart() {
        final Window window = getDialog().getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        super.onStart();
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