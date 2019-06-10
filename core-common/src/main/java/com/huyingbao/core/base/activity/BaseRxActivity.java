package com.huyingbao.core.base.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.huyingbao.core.arch.model.RxError;
import com.huyingbao.core.arch.model.RxLoading;
import com.huyingbao.core.arch.model.RxRetry;
import com.huyingbao.core.arch.store.RxActivityStore;
import com.huyingbao.core.arch.view.RxFluxActivity;
import com.huyingbao.core.base.BaseView;
import com.huyingbao.core.common.R;
import com.huyingbao.core.common.action.CommonActionCreator;
import com.huyingbao.core.common.dialog.CommonLoadingDialog;
import com.huyingbao.core.common.model.CommonException;

import org.greenrobot.eventbus.Subscribe;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.Lazy;


/**
 * 带有toolbar的Activity父类
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public abstract class BaseRxActivity<T extends RxActivityStore> extends RxFluxActivity<T> implements BaseView {
    static {//Vector使用
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Inject
    protected CommonActionCreator mCommonActionCreator;
    @Inject
    Lazy<CommonLoadingDialog> mLoadingDialogLazy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initActionBar();
        afterCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    /**
     * 初始化ActionBar，默认使用R.id.tlb_top Toolbar取代ActionBar，
     */
    protected void initActionBar() {
        View view = getWindow().getDecorView();
        Toolbar toolbar = view.findViewById(R.id.tlb_top);
        if (toolbar != null) {
            //使用Toolbar取代原本的actionbar
            setSupportActionBar(toolbar);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // 点击返回图标事件
            case android.R.id.home:
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                    return true;
                }
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 接收RxError，粘性，该方法不经过RxStore, 由RxFluxView直接处理
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
        snackbar.setAction("Retry", v -> mCommonActionCreator.postRetryAction(rxRetry)).show();
    }

    /**
     * 显示进度对话框
     * 接收RxLoading，粘性
     * 该方法不经过RxStore,
     * 由RxFluxView直接处理
     */
    @Subscribe(sticky = true)
    public void onRxLoading(@NonNull RxLoading rxLoading) {
        Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(rxLoading.getTag());
        if (fragmentByTag == null && rxLoading.isLoading()) {
            //显示进度框
            mLoadingDialogLazy.get().show(getSupportFragmentManager(), rxLoading.getTag());
            return;
        }
        if (fragmentByTag instanceof CommonLoadingDialog && !rxLoading.isLoading()) {
            //隐藏进度框
            ((CommonLoadingDialog) fragmentByTag).dismiss();
        }
    }
}