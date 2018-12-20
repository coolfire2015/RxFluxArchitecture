package com.huyingbao.core.common.view;

import android.os.Bundle;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public interface CommonView {
    int getLayoutId();

    void afterCreate(Bundle savedInstanceState);
}
