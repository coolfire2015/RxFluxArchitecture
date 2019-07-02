package com.huyingbao.module.github.module

import it.cosenonjaviste.daggermock.DaggerMock


/**
 * 全局静态方法,提供依赖注入器实例对象
 */
object MockUtils {
    var component: MockComponent? = null
}

/**
 * DaggerMock Rule
 *
 * 1.初始化测试类里面的所有用[org.mockito.Mock]和[org.mockito.Spy]标注的对象
 *
 * 2.通过反射的方式得到[MockModule]的所有provider方法，如果有某个方法的返回值是[org.mockito.Mock]和[org.mockito.Spy]标注的对象，那么就使用Mockito返回mock的对象。
 *
 * 3.使用[MockModule]来构建一个[MockComponent]，并且放到[MockUtils]里面去。
 */
fun mockDaggerRule() = DaggerMock.rule<MockComponent>(MockModule()) {
    set {
        MockUtils.component = it
    }
}