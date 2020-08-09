package com.huyingbao.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注编译自动生成的{@link AppLifecycleObserver}的索引类。
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface AppIndex {
    /**
     * @return 所有被{@link AppLifecycleObserver}标注的androidx.lifecycle.LifecycleObserver的实现类的标准名组成的字符串数组
     */
    String[] observers() default {};
}
