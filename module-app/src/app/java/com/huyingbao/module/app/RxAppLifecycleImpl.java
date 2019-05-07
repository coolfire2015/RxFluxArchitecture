package com.huyingbao.module.app;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.huyingbao.core.arch.RxAppLifecycle;
import com.huyingbao.module.gan.action.GanAppLifecyle;
import com.huyingbao.module.wan.action.WanAppLifecyle;

public class RxAppLifecycleImpl implements RxAppLifecycle {

    private GanAppLifecyle ganAppLifecyle;
    private WanAppLifecyle wanAppLifecyle;

    RxAppLifecycleImpl() {
        ganAppLifecyle = new GanAppLifecyle();
        wanAppLifecyle = new WanAppLifecyle();
        if (Log.isLoggable("Glide", Log.DEBUG)) {
            Log.d("Glide", "Discovered AppGlideModule from annotation: com.huyingbao.module.app.ImageAppGlideModule");
            Log.d("Glide", "Discovered LibraryGlideModule from annotation: com.huyingbao.module.gan.action.GanGlideModule");
            Log.d("Glide", "Discovered LibraryGlideModule from annotation: com.huyingbao.module.wan.action.WanGlideModule");
        }
    }

    @Override
    public void attachBaseContext(@NonNull Context base) {
        ganAppLifecyle.attachBaseContext(base);
        wanAppLifecyle.attachBaseContext(base);
    }

    @Override
    public void onCreate(@NonNull Application application) {
        wanAppLifecyle.onCreate(application);
        ganAppLifecyle.onCreate(application);
    }

    @Override
    public void onTerminate(@NonNull Application application) {
        wanAppLifecyle.onTerminate(application);
        ganAppLifecyle.onTerminate(application);
    }
}
