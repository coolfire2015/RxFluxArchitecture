package com.huyingbao.module.github.module

import it.cosenonjaviste.daggermock.DaggerMock

/**
 * 1:初始化一个测试类里面的所有用@Mock field为mock对象(loginPresenter)
 *
 *
 * 2:mock AppModule，通过反射的方式得到AppModule的所有provider方法，如果有某个方法的返回值是一个LoginPresenter，那么就使用Mockito，让这个方法（provideLoginPresenter(...))被调用时，返回我们在测试类里面定义的mock loginPresenter。
 *
 *
 * 3:使用这个mock AppModule来构建一个Component，并且放到ComponentHolder里面去。
 *
 *
 * Created by liujunfeng on 2019/4/4.
 */
fun mockDaggerRule() = DaggerMock.rule<MockComponent>(MockModule()) {
    set {
        MockUtils.component = it
    }
}