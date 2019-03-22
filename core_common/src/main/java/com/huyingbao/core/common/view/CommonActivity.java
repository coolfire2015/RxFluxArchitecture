package com.huyingbao.core.common.view;

import android.os.Bundle;
import android.view.MenuItem;

import com.huyingbao.core.common.R;
import com.huyingbao.core.common.util.ActivityUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import butterknife.ButterKnife;


/**
 * 带有toolbar的Activity父类
 * Created by liujunfeng on 2019/1/1.
 */
public abstract class CommonActivity extends AppCompatActivity implements CommonView {
    static {//Vector使用
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    /**
     * 提供activity需要显示的fragment
     *
     * @return
     */
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
