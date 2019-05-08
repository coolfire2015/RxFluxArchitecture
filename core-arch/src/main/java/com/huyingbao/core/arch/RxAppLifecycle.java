package com.huyingbao.core.arch;

import android.app.Application;

/**
 * 跟随App生命周期
 */
public interface RxAppLifecycle {
    /**
     * 跟随onCreate方法
     *
     * @param application
     */
    void onCreate(Application application);
}
