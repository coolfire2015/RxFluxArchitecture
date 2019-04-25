package com.huyingbao.core.base.rxview;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.huyingbao.core.arch.model.RxError;
import com.huyingbao.core.arch.model.RxLoading;
import com.huyingbao.core.arch.model.RxRetry;
import com.huyingbao.core.arch.store.RxActivityStore;
import com.huyingbao.core.arch.view.RxFluxView;
import com.huyingbao.core.arch.view.RxSubscriberView;
import com.huyingbao.core.base.view.BaseActivity;
import com.huyingbao.core.common.R;
import com.huyingbao.core.common.action.CommonActionCreator;
import com.huyingbao.core.common.dialog.CommonLoadingDialog;
import com.huyingbao.core.common.model.CommonException;
import com.huyingbao.core.utils.ClassUtils;

import org.greenrobot.eventbus.Subscribe;

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
public abstract class BaseRxActivity<T extends RxActivityStore> extends BaseActivity
        implements RxFluxView, RxSubscriberView, HasSupportFragmentInjector {
    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    @Inject
    DispatchingAndroidInjector<Fragment> mChildFragmentInjector;
    @Inject
    Lazy<CommonActionCreator> mCommonActionCreatorLazy;
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

    /**
     * 接收RxError，粘性
     * 该方法不经过RxStore,
     * 由RxFluxView直接处理
     */
    @Subscribe(sticky = true)
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
    @Subscribe(sticky = true)
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
    @Subscribe(sticky = true)
    public void onRxLoading(@NonNull RxLoading rxLoading) {
        Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(rxLoading.getTag());
        if (fragmentByTag == null && rxLoading.isLoading()) {
            //显示进度框
            mCommonLoadingDialogLazy.get().show(getSupportFragmentManager(), rxLoading.getTag());
            return;
        }
        if (fragmentByTag instanceof CommonLoadingDialog && !rxLoading.isLoading()) {
            //隐藏进度框
            ((CommonLoadingDialog) fragmentByTag).dismiss();
        }
    }
}