package com.huyingbao.core.common.rxview;

import android.content.Context;

import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.arch.model.RxError;
import com.huyingbao.core.arch.model.RxLoading;
import com.huyingbao.core.arch.model.RxRetry;
import com.huyingbao.core.arch.view.RxFluxView;
import com.huyingbao.core.common.view.CommonFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.AndroidSupportInjection;

/**
 * @author liujunfeng
 * @date 2019/1/1
 */
public abstract class CommonRxFragment<T extends ViewModel> extends CommonFragment implements RxFluxView {
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
        super.onAttach(context);
    }

    /**
     * 接收RxChange，粘性
     */
    @Override
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onRxChanged(@NonNull RxChange rxChange) {
        //收到后，移除粘性通知
        EventBus.getDefault().removeStickyEvent(rxChange);
    }

    /**
     * 接收RxError，粘性
     * 该方法不经过RxStore,
     * 由RxFluxView直接处理
     */
    @Override
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onRxError(@NonNull RxError rxError) {
        //收到后，移除粘性通知
        EventBus.getDefault().removeStickyEvent(rxError);
    }

    /**
     * 接收RxLoading，粘性
     * 该方法不经过RxStore,
     * 由RxFluxView直接处理
     */
    @Override
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onRxRetry(@NonNull RxRetry rxRetry) {
        //收到后，移除粘性通知
        EventBus.getDefault().removeStickyEvent(rxRetry);
    }

    /**
     * 接收RxLoading，粘性
     * 该方法不经过RxStore,
     * 由RxFluxView直接处理
     */
    @Override
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onRxLoading(@NonNull RxLoading rxLoading) {
        //收到后，移除粘性通知
        EventBus.getDefault().removeStickyEvent(rxLoading);
    }
}
