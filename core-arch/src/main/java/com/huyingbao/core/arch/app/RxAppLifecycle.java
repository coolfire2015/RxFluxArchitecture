package com.huyingbao.core.arch.app;

import android.app.Application;

import androidx.annotation.NonNull;

/**
 * 跟随App生命周期
 */
public interface RxAppLifecycle {
    /**
     * 跟随onCreate方法
     *
     * @param application
     */
    void onCreate(@NonNull Application application);
}
