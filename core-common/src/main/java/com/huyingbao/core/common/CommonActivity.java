package com.huyingbao.core.common;

import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.MalformedJsonException;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.huyingbao.core.action.RxError;
import com.huyingbao.core.dispatcher.RxViewDispatch;
import com.huyingbao.core.model.RxHttpException;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import butterknife.BindView;


/**
 * 带有toolbar的Activity父类
 * Created by liujunfeng on 2017/12/7.
 */
public abstract class CommonActivity extends RxFluxActivity implements RxViewDispatch {
    static {//Vector使用
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @BindView(R2.id.tv_top_title)
    protected TextView mTvTopTitle;
    @BindView(R2.id.tlb_top)
    protected Toolbar mToolbarTop;
    @BindView(R2.id.abl_top)
    protected AppBarLayout mAppBarLayoutTop;

    @Override
    public int getLayoutId() {
        return R.layout.activity_fragment_base;
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

    @Override
    public void setTitle(CharSequence title) {
        mTvTopTitle.setText(title == null ? getTitle() : title);
    }

    /**
     * 该方法不经过store,由activity直接处理
     * rxflux中对错误的处理
     */
    @Override
    public void onRxError(@NonNull RxError error) {
        switch (error.getAction().getType()) {
            default:
                handleThrowable(error);
                break;
        }
    }

    private void handleThrowable(@NonNull RxError error) {
        Throwable throwable = error.getThrowable();
        // 自定义异常
        if (throwable instanceof RxHttpException) {
            String message = ((RxHttpException) throwable).message();
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

    /**
     * 显示短暂的Toast
     *
     * @param text
     */
    protected void showShortToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void setToolbar(boolean backAble) {
        //取代原本的actionbar
        setSupportActionBar(mToolbarTop);
        //设置actionbar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;
        //显示右侧返回图标
        actionBar.setDisplayHomeAsUpEnabled(backAble);
        if (backAble) actionBar.setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_material);
        //不显示home图标
        actionBar.setDisplayShowHomeEnabled(false);
        //不显示标题
        actionBar.setDisplayShowTitleEnabled(false);
    }

    public void initActionBar(String title, boolean backAble) {
        //设置标题
        setTitle(title);
        // 设置toolbar
        setToolbar(backAble);
    }

    public void initActionBar(String title) {
        this.initActionBar(title, true);
    }

    public void initActionBar() {
        this.initActionBar(null, true);
    }
}
