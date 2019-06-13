package com.huyingbao.core.base.activity;

import android.os.Bundle;

import androidx.annotation.NavigationRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;

import com.huyingbao.core.arch.store.RxActivityStore;
import com.huyingbao.core.base.BaseView;
import com.huyingbao.core.common.R;


/**
 * 使用{@link androidx.navigation.Navigation}管理Fragment
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public abstract class BaseRxNavActivity<T extends RxActivityStore> extends BaseRxActivity<T> implements BaseView {
    @Override
    public int getLayoutId() {
        return R.layout.base_activity_nav;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment();
    }

    /**
     * 设置导航组件{@link androidx.navigation.Navigation}
     */
    private void initFragment() {
        //添加显示的Fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        //从fragment队列中获取资源ID标识的fragment，如果不存在，则返回
        Fragment navHostFragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (!(navHostFragment instanceof NavHostFragment)) {
            return;
        }
        //设置导航解析文件
        ((NavHostFragment) navHostFragment).getNavController().setGraph(getGraphId());
    }

    /**
     * 导航资源文件
     *
     * @return {@link androidx.navigation.Navigation}资源文件
     */
    @NavigationRes
    public abstract int getGraphId();
}