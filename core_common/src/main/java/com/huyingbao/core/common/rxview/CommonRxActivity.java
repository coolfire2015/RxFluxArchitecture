package com.huyingbao.core.common.rxview;

import android.os.Bundle;
import android.widget.Toast;

import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.arch.model.RxError;
import com.huyingbao.core.arch.model.RxLoading;
import com.huyingbao.core.arch.store.RxActivityStore;
import com.huyingbao.core.arch.view.RxFluxView;
import com.huyingbao.core.common.dialog.CommonLoadingDialog;
import com.huyingbao.core.common.model.CommonHttpException;
import com.huyingbao.core.common.view.CommonActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.inject.Inject;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.Lazy;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;


/**
 * Created by liujunfeng on 2019/1/1.
 */
public abstract class CommonRxActivity<T extends RxActivityStore> extends CommonActivity implements RxFluxView, HasSupportFragmentInjector {
    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    @Inject
    DispatchingAndroidInjector<Fragment> mChildFragmentInjector;
    @Inject
    Lazy<CommonLoadingDialog> mCommonLoadingDialogLazy;

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
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (!(genericSuperclass instanceof ParameterizedType)) {
            return null;
        }
        Class<T> tClass = (Class<T>) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        mStore = ViewModelProviders.of(this, mViewModelFactory).get(tClass);
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

    /**
     * 接收RxChange，粘性
     */
    @Override
    @CallSuper//强制子类复写该方法时调用父方法
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
        Throwable throwable = rxError.getThrowable();
        if (throwable instanceof CommonHttpException) {
            Toast.makeText(this, ((CommonHttpException) throwable).message(), Toast.LENGTH_SHORT).show();
        } else if (throwable instanceof retrofit2.HttpException) {
            Toast.makeText(this, ((retrofit2.HttpException) throwable).code() + ":服务器问题", Toast.LENGTH_SHORT).show();
        } else if (throwable instanceof SocketException) {
            Toast.makeText(this, "网络异常!", Toast.LENGTH_SHORT).show();
        } else if (throwable instanceof UnknownHostException) {
            Toast.makeText(this, "网络异常!", Toast.LENGTH_SHORT).show();
        } else if (throwable instanceof SocketTimeoutException) {
            Toast.makeText(this, "连接超时!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, throwable.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 接收RxRxLoading，粘性
     * 该方法不经过RxStore,
     * 由RxFluxView直接处理
     */
    @Override
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onRxLoading(@NonNull RxLoading rxLoading) {
        //收到后，移除粘性通知
        EventBus.getDefault().removeStickyEvent(rxLoading);
        if (rxLoading.isLoading()) {//显示进度框
            mCommonLoadingDialogLazy.get().setRxLoading(rxLoading);
            mCommonLoadingDialogLazy.get().show(getSupportFragmentManager(), rxLoading.getTag());
        } else {//隐藏进度框
            mCommonLoadingDialogLazy.get().dismiss();
        }
    }
}
