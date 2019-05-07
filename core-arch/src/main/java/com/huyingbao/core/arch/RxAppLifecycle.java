package com.huyingbao.core.arch;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

public interface RxAppLifecycle {
    void onCreate(@NonNull Application application);
}
