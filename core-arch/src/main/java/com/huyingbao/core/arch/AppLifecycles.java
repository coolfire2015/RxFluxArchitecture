package com.huyingbao.core.arch;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

public interface AppLifecycles {
    void attachBaseContext(@NonNull Context base);

    void onCreate(@NonNull Application application);

    void onTerminate(@NonNull Application application);
}
