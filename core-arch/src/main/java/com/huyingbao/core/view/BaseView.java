package com.huyingbao.core.view;

import android.os.Bundle;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public interface BaseView {
    /**
     * 设置view
     *
     * @return
     */
    int getLayoutId();

    /**
     * 创建之后的操作
     *
     * @param savedInstanceState
     */
    void afterCreate(Bundle savedInstanceState);
}
