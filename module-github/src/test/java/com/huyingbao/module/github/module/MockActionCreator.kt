package com.huyingbao.module.github.module

import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.test.utils.RxJavaRule
import org.junit.Rule
import org.mockito.Spy
import org.mockito.junit.MockitoJUnit

/**
 * Created by liujunfeng on 2019/4/3.
 */
open class MockActionCreator {
    @get:Rule
    var rxJavaRule = RxJavaRule()
    @get:Rule
    var mockitoRule = MockitoJUnit.rule()
    @get:Rule
    var mockDaggerRule = mockDaggerRule()
    @Spy
    lateinit var rxDispatcher: RxDispatcher
    @Spy
    lateinit var rxActionManager: RxActionManager
}