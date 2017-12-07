package com.huyingbao.core.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Java中，单例通常保存在一个静态域中，这样的单例往往要等到虚拟机关闭时候，该单例所占用的资源才释放。
 * 但是，Dagger通过PerActivity创建出来的单例并不保持在静态域上，而是保留在Component(ActivityComponent)实例中
 * Created by liujunfeng on 2017/12/7.
 */
@Scope
@Retention(RUNTIME)
public @interface PerActivity {
}
