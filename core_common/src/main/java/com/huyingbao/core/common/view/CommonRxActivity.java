package com.huyingbao.core.common.view;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.arch.model.RxError;
import com.huyingbao.core.arch.store.RxActivityStore;
import com.huyingbao.core.arch.view.RxFluxView;
import com.huyingbao.core.common.R;
import com.huyingbao.core.common.model.CommonHttpException;
import com.huyingbao.core.common.util.ActivityUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.ParameterizedType;

import javax.inject.Inject;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;


/**
 * 带有toolbar的Activity父类
 * Created by liujunfeng on 2019/1/1.
 */
public abstract class CommonRxActivity<T extends RxActivityStore> extends AppCompatActivity implements CommonView, RxFluxView, HasSupportFragmentInjector {
    static {//Vector使用
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Inject
    protected ViewModelProvider.Factory mViewModelFactory;
    @Inject
    DispatchingAndroidInjector<Fragment> mChildFragmentInjector;

    private T mStore;

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return mChildFragmentInjector;
    }

    @Nullable
    @Override
    public T getRxStore() {
        if (mStore == null) {
            Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            mStore = ViewModelProviders.of(this, mViewModelFactory).get(tClass);
        }
        return mStore;
    }

    /**
     * 提供activity需要显示的fragment
     *
     * @return
     */
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        Log.v("RxFlux", "1.1-onCreateActivity");
        setContentView(getLayoutId());
        afterCreate(savedInstanceState);
        ButterKnife.bind(this);
        //添加显示的Fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        //从fragment队列中获取资源ID标识的fragment
        Fragment fragment = fragmentManager.findFragmentById(R.id.fl_content);
        if (fragment != null) return;
        fragmentManager.beginTransaction().add(R.id.fl_content, createFragment()).commit();
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
        Throwable throwable = error.getThrowable();
        // 自定义异常
        if (throwable instanceof CommonHttpException) {
            String message = ((CommonHttpException) throwable).message();
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, throwable.toString(), Toast.LENGTH_SHORT).show();
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
}
