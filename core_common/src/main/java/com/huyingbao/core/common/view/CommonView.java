package com.huyingbao.core.common.view;

import android.os.Bundle;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public interface CommonView {
    int getLayoutId();

    void afterCreate(Bundle savedInstanceState);
}
