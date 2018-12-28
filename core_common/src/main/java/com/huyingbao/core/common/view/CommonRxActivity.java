package com.huyingbao.core.common.view;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.arch.model.RxError;
import com.huyingbao.core.arch.store.RxStoreForActivity;
import com.huyingbao.core.arch.view.RxFluxActivity;
import com.huyingbao.core.arch.view.RxFluxView;
import com.huyingbao.core.common.R;
import com.huyingbao.core.common.util.ActivityUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
        //收到后，移除粘性通知
        EventBus.getDefault().removeStickyEvent(rxChange);
    }

    /**
     * 接收RxError，粘性
     * 该方法不经过store,
     * 由activity直接处理
     */
    @Override
    @Subscribe(sticky = true)
    public void onRxError(@NonNull RxError error) {
        //收到后，移除粘性通知
        EventBus.getDefault().removeStickyEvent(error);
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
}
