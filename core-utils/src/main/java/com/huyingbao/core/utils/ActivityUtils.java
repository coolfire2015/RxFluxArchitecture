package com.huyingbao.core.utils;

import android.text.TextUtils;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public class ActivityUtils {
    /**
     * 设置需要显示的Fragment
     *
     * @param activity        宿主Activity
     * @param contentId       容器ID
     * @param fragmentNew     需要显示的Fragment
     * @param isHideOrReplace {@code true} 隐藏并可回退到已经存在；
     *                        {@code false} 替换已经存在
     */
    public static void setFragment(
            FragmentActivity activity,
            @IdRes int contentId,
            @NonNull Fragment fragmentNew,
            boolean isHideOrReplace) {
        //如果已经添加则返回
        if (fragmentNew.isAdded()) {
            return;
        }
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
        //存在旧的Fragment
        String fragmentName = fragment.getClass().getSimpleName();
        String fragmentNewName = fragmentNew.getClass().getSimpleName();
        //旧的Fragment和新的Fragment同为同一个Fragment的实例对象
        if (TextUtils.equals(fragmentName, fragmentNewName)) {
            return;
        }
        //旧的Fragment和新的不同
        //隐藏并可回退到已经存在的Fragment
        if (isHideOrReplace) {
            fragmentManager.beginTransaction()
                    .addToBackStack(fragment.getClass().getName())
                    .hide(fragment)
                    .add(contentId, fragmentNew)
                    .commit();

        } else {//替换已经存在的Fragment
            fragmentManager.beginTransaction()
                    .replace(contentId, fragmentNew)
                    .commit();
        }
    }
}
