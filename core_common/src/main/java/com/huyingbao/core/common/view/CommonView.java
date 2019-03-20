package com.huyingbao.core.common.view;

import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public interface CommonView {
    int getLayoutId();

    void afterCreate(@Nullable Bundle savedInstanceState);
}
