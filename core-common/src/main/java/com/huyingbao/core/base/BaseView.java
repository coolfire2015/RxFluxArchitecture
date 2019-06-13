package com.huyingbao.core.base;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

/**
 * View抽象接口
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public interface BaseView {
    /**
     * 获取对应布局文件ID
     *
     * @return 布局文件
     */
    @LayoutRes
    int getLayoutId();

    /**
     * View层创建之后调用方法
     *
     * @param savedInstanceState
     */
    void afterCreate(@Nullable Bundle savedInstanceState);
}
