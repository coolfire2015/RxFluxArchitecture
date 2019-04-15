package com.huyingbao.core.base.rxview;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.huyingbao.core.arch.model.RxError;
import com.huyingbao.core.arch.model.RxLoading;
import com.huyingbao.core.arch.model.RxRetry;
import com.huyingbao.core.arch.store.RxActivityStore;
import com.huyingbao.core.arch.view.RxFluxView;
import com.huyingbao.core.base.view.BaseActivity;
import com.huyingbao.core.common.R;
import com.huyingbao.core.common.action.CommonActionCreator;
import com.huyingbao.core.common.dialog.CommonLoadingDialog;
import com.huyingbao.core.common.model.CommonException;

import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
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
public abstract class BaseRxActivity<T extends RxActivityStore> extends BaseActivity implements RxFluxView, HasSupportFragmentInjector {
    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    @Inject
    DispatchingAndroidInjector<Fragment> mChildFragmentInjector;
    @Inject
    Lazy<CommonLoadingDialog> mCommonLoadingDialogLazy;
    @Inject
    Lazy<CommonActionCreator> mCommonActionCreatorLazy;

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
     * 接收RxError，粘性
     * 该方法不经过RxStore,
     * 由RxFluxView直接处理
     */
    @Subscribe()
    public void onRxError(@NonNull RxError rxError) {
        Throwable throwable = rxError.getThrowable();
        if (throwable instanceof CommonException) {
            Toast.makeText(this, ((CommonException) throwable).message(), Toast.LENGTH_SHORT).show();
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
     * 接收RxLoading，粘性
     * 该方法不经过RxStore,
     * 由RxFluxView直接处理
     */
    @Subscribe()
    public void onRxRetry(@NonNull RxRetry rxRetry) {
        CoordinatorLayout coordinatorLayout = findViewById(R.id.cdl_content);
        if (coordinatorLayout == null) {
            return;
        }
        Snackbar snackbar = Snackbar.make(coordinatorLayout, rxRetry.getTag(), Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Retry", v -> mCommonActionCreatorLazy.get().postRetryAction(rxRetry)).show();
    }

    /**
     * 接收RxLoading，粘性
     * 该方法不经过RxStore,
     * 由RxFluxView直接处理
     */
    @Subscribe()
    public void onRxLoading(@NonNull RxLoading rxLoading) {
        //显示进度框
        if (rxLoading.isLoading()) {
            mCommonLoadingDialogLazy.get().setRxLoading(rxLoading);
            mCommonLoadingDialogLazy.get().show(getSupportFragmentManager(), rxLoading.getTag());
        } else {//隐藏进度框
            mCommonLoadingDialogLazy.get().dismiss();
        }
    }
}