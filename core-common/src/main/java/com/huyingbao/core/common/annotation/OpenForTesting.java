package com.huyingbao.core.common.annotation;

/**
 * Created by liujunfeng on 2019/5/30.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@AllOpen
@Target(ElementType.TYPE)
public @interface OpenForTesting {
}