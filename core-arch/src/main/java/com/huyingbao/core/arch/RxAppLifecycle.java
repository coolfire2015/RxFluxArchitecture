package com.huyingbao.core.arch;

import android.app.Application;

import androidx.annotation.NonNull;

public interface RxAppLifecycle {
    void onCreate(@NonNull Application application);
}
