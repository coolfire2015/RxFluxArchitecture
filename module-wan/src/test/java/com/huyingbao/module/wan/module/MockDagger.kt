package com.huyingbao.module.wan.module

import it.cosenonjaviste.daggermock.DaggerMock

/**
 * 全局静态方法,提供依赖注入器实例对象
 */
object MockUtils {
    var component: MockComponent? = null
}

/**
 * 动态创建 Module 子类的 JUnit 规则
 *
 * 1.动态创建了一个[MockModule]的子类，返回在测试中定义并使用[org.mockito.Mock]和[org.mockito.Spy]标注的虚拟对象 ，而不是真实的对象。
 *
 * 2.使用[MockModule]来构建一个[MockComponent]，并且放到[MockUtils]里面去。
 */
fun mockDaggerRule() = DaggerMock.rule<MockComponent>(MockModule()) {
    set {
        MockUtils.component = it
    }
}