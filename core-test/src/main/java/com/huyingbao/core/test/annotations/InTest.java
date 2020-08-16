package com.huyingbao.core.test.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 只在单元测试中使用的别名注解
 * <p>
 * Created by liujunfeng on 2019/8/21.
 */
@Qualifier
@Documented
@Retention(RUNTIME)
public @interface InTest {
}
