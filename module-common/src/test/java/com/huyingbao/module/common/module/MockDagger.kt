package com.huyingbao.module.common.module

import it.cosenonjaviste.daggermock.DaggerMock

/**
 * 全局静态方法,提供依赖注入器实例对象。
 *
 * Created by liujunfeng on 2019/7/1.
 */
object MockUtils {
    var component: MockComponent? = null
}

/**
 * 动态创建 Module 子类的 JUnit 规则
 *
 * 1.动态创建了一个[MockModule]的子类，返回在测试中定义并使用[org.mockito.Mock]和[org.mockito.Spy]标注的虚拟对象 ，而不是真实的对象。
 *
 * 2.Mock [MockModule]，通过反射的方式得到[MockModule]的所有[dagger.Provides]方法，如果有某个方法的返回值是[org.mockito.Mock]和[org.mockito.Spy]标注的虚拟对象，
 * 那么就使用Mockito，让这个[dagger.Provides]方法被调用时，返回虚拟对象。
 *
 * 3.使用[MockModule]来构建一个[MockComponent]，并且放到[MockUtils]里面去。
 */
fun mockDaggerRule() = DaggerMock.rule<MockComponent>(MockModule()) {
    set {
        MockUtils.component = it
    }
}