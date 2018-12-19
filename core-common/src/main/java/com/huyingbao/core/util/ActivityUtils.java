package com.huyingbao.core.util;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public class ActivityUtils {
    public static void addFragment(@NonNull FragmentManager fragmentManager,
                                   @NonNull Fragment fragment,
                                   @IdRes int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    public static void addAndHideFragment(@NonNull FragmentManager fragmentManager,
                                          @NonNull Fragment fragment,
                                          @IdRes int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment oldFragment = fragmentManager.findFragmentById(frameId);
        if (oldFragment != null)
            transaction.addToBackStack(oldFragment.getClass().getName()).hide(oldFragment);
        transaction.add(frameId, fragment);
        transaction.commit();
    }
}
