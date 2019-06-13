package com.huyingbao.core.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * kotlin open 单元测试
 * <p>
 * Created by liujunfeng on 2019/5/30.
 */
@AllOpen
@Target(ElementType.TYPE)
public @interface OpenForTesting {
}