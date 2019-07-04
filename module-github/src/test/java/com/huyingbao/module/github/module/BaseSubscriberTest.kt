package com.huyingbao.module.github.module

import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.test.utils.RxJavaRule
import org.junit.Rule
import org.mockito.Spy
import org.mockito.junit.MockitoJUnit

/**
 * 所有Subscriber测试类的父类
 *
 * Created by liujunfeng on 2019/4/3.
 */
abstract class BaseSubscriberTest {
    /**
     * 初始化DaggerMock
     */
    @get:Rule
    var mockDaggerRule = mockDaggerRule()
    /**
     * 初始化[org.mockito.Spy]和[org.mockito.Mock]标注的对象
     */
    @get:Rule
    var mockitoRule = MockitoJUnit.rule()
    /**
     * [com.huyingbao.core.arch.store.RxStore]和[com.huyingbao.core.arch.view.RxSubscriberView]
     * 自动注册订阅和取消订阅
     */
    @get:Rule
    var actionCreatorRule = BaseSubscriberRule()
    /**
     * RxJava调度器异步转为同步
     */
    @get:Rule
    var rxJavaRule = RxJavaRule()
    /**
     * 有无参构造函数
     */
    @Spy
    lateinit var rxDispatcher: RxDispatcher
    /**
     * 有无参构造函数
     */
    @Spy
    lateinit var rxActionManager: RxActionManager

    /**
     * 获取所有需要管理订阅的 [com.huyingbao.core.arch.store.RxStore]和[com.huyingbao.core.arch.view.RxSubscriberView]
     * 实现类对象列表
     */
    abstract fun getSubscriberList(): List<Any>
}