package com.huyingbao.core.base.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.huyingbao.core.arch.store.RxActivityStore;
import com.huyingbao.core.base.BaseView;
import com.huyingbao.core.common.R;
import com.huyingbao.core.utils.ActivityUtils;


/**
 * 带有toolbar的Activity父类
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public abstract class BaseRxFragActivity<T extends RxActivityStore> extends BaseRxActivity<T> implements BaseView {
    @Override
    public int getLayoutId() {
        return R.layout.base_frag_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment();
    }

    /**
     * 设置需要展示的Fragment
     */
    private void initFragment() {
        //如果不存在容纳Fragment的FrameLayout，则返回
        if (findViewById(R.id.fl_container) == null) {
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        //从fragment队列中获取资源ID标识的fragment
        Fragment fragmentOld = fragmentManager.findFragmentById(R.id.fl_container);
        //如果已经存在fragment或者创建fragment方法为空,则返回
        if (fragmentOld != null) {
            return;
        }
        //使用新的Fragment，如果为空则返回
        Fragment fragmentNew = createFragment();
        if (fragmentNew == null) {
            return;
        }
        //使用fragment类名作为tag
        String tag = fragmentNew.getClass().getSimpleName();
        fragmentManager.beginTransaction().add(R.id.fl_container, fragmentNew, tag).commit();
    }

    /**
     * 无旧的Fragment，添加新的Fragment
     * 有旧的Fragment，隐藏旧的Fragment，添加新的Fragment
     *
     * @param fragment
     */
    protected void addFragmentHideExisting(Fragment fragment) {
        ActivityUtils.setFragment(this, R.id.fl_container, fragment, true);
    }

    /**
     * 无旧的Fragment，添加新的Fragment
     * 有旧的Fragment，用新的Fragment替换旧的Fragment
     *
     * @param fragment
     */
    protected void addFragmentReplaceExisting(Fragment fragment) {
        ActivityUtils.setFragment(this, R.id.fl_container, fragment, false);
    }

    /**
     * 提供activity需要显示的fragment
     *
     * @return
     */
    protected abstract Fragment createFragment();
}