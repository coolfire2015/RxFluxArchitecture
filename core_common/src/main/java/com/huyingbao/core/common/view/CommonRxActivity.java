package com.huyingbao.core.common.view;

import android.os.Bundle;
import android.util.Log;
import android.util.MalformedJsonException;
import android.view.MenuItem;
import android.widget.Toast;

import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.arch.model.RxError;
import com.huyingbao.core.arch.store.RxStoreForActivity;
import com.huyingbao.core.arch.view.RxFluxActivity;
import com.huyingbao.core.arch.view.RxFluxView;
import com.huyingbao.core.common.R;
import com.huyingbao.core.common.model.CommonHttpException;
import com.huyingbao.core.common.util.ActivityUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;


/**
 * 带有toolbar的Activity父类
 * Created by liujunfeng on 2017/12/7.
 */
public abstract class CommonRxActivity<T extends RxStoreForActivity> extends RxFluxActivity implements CommonView, RxFluxView {
    static {//Vector使用
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Nullable
    @Override
    public abstract T getRxStore();

    /**
     * 提供activity需要显示的fragment
     *
     * @return
     */
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("RxFlux", "1.1-onCreateActivity");
        setContentView(getLayoutId());
        addFragmentReplaceExisting(createFragment());
        ButterKnife.bind(this);
        afterCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.common_activity_fragment_base;
    }

    /**
     * 接收RxChange，粘性
     */
    @Override
    @CallSuper//强制子类复写该方法时调用父方法
    @Subscribe(sticky = true)
    public void onRxChanged(@NonNull RxChange rxChange) {
        EventBus.getDefault().removeStickyEvent(rxChange);
    }

    /**
     * 接收RxError，粘性
     * 该方法不经过store,
     * 由activity直接处理
     * rxflux中对错误的处理
     */
    @Override
    @Subscribe(sticky = true)
    public void onRxError(@NonNull RxError error) {
        EventBus.getDefault().removeStickyEvent(error);
        Throwable throwable = error.getThrowable();
        // 自定义异常
        if (throwable instanceof CommonHttpException) {
            String message = ((CommonHttpException) throwable).message();
            showShortToast(message);
        } else if (throwable instanceof retrofit2.HttpException) {
            showShortToast(((retrofit2.HttpException) throwable).code() + ":服务器问题");
        } else if (throwable instanceof SocketException) {
            showShortToast("连接服务器失败，请检查网络状态和服务器地址配置！");
        } else if (throwable instanceof SocketTimeoutException) {
            showShortToast("连接服务器超时，请检查网络状态和服务器地址配置！");
        } else if (throwable instanceof UnknownHostException) {
            showShortToast("请输入正确的服务器地址！");
        } else if (throwable instanceof MalformedJsonException) {
            showShortToast("请检查网络状态！");
        } else {
            showShortToast(throwable == null ? "未知错误" : throwable.toString());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 点击返回图标事件
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
     * 无旧的Fragment，添加新的Fragment
     * 有旧的Fragment，隐藏旧的Fragment，添加新的Fragment
     *
     * @param fragment
     */
    protected void addFragmentHideExisting(Fragment fragment) {
        ActivityUtils.setFragment(this, R.id.fl_content, fragment, true);
    }

    /**
     * 无旧的Fragment，添加新的Fragment
     * 有旧的Fragment，用新的Fragment替换旧的Fragment
     *
     * @param fragment
     */
    protected void addFragmentReplaceExisting(Fragment fragment) {
        ActivityUtils.setFragment(this, R.id.fl_content, fragment, false);
    }

    /**
     * 显示短暂的Toast
     *
     * @param text
     */
    protected void showShortToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
