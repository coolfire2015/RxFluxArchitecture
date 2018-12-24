package com.huyingbao.core.common.util;

import com.orhanobut.logger.Logger;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public class ActivityUtils {
    /**
     * 设置需要显示的Fragment
     *
     * @param activity                  宿主Activity
     * @param contentId                 容器ID
     * @param fragmentNew               需要显示的Fragment
     * @param isHideExistingFragment    true：隐藏并可回退到已经存在的Fragment
     * @param isReplaceExistingFragment true：替换已经存在的Fragment
     */
    public static void setFragment(
            FragmentActivity activity,
            @IdRes int contentId,
            @NonNull Fragment fragmentNew,
            boolean isHideExistingFragment,
            boolean isReplaceExistingFragment) {
        //1:管理fragment队列
        //2:管理fragment事务回退栈
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        //从fragment队列中获取资源ID标识的fragment
        Fragment fragment = fragmentManager.findFragmentById(contentId);
        //如果不存在旧的Fragment，直接添加显示新的Fragment
        if (fragment == null) {
            //fragment事务被用来添加,移除,附加,分离或替换fragment队列中的fragment
            //资源ID标识UI fragment是FragmentManager的一种内部实现机制
            //添加fragment供FragmentManager管理时
            //onAttach(Context),onCreate(Bundle)和onCreateView(...)方法会被调用
            fragmentManager.beginTransaction()
                    .add(contentId, fragmentNew)
                    .commit();
            return;
        }
        //隐藏并可回退到已经存在的Fragment
        if (isHideExistingFragment) {
            fragmentManager.beginTransaction()
                    .addToBackStack(fragment.getClass().getName())
                    .hide(fragment)
                    .add(contentId, fragmentNew)
                    .commit();
            return;
        }
        //替换已经存在的Fragment
        if (isReplaceExistingFragment) {
            fragmentManager.beginTransaction()
                    .replace(contentId, fragmentNew)
                    .commit();
        } else {
            Logger.e("旧的Fragment正在显示，不显示新的Fragment");
        }
    }
}
