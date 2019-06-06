package com.huyingbao.module.wan.module;

import it.cosenonjaviste.daggermock.DaggerMockRule;

/**
 * 1.初始化一个测试类里面的所有用@Mock field为mock对象(loginPresenter)
 * <p>
 * 2.mock AppModule，通过反射的方式得到AppModule的所有provider方法，如果有某个方法的返回值是一个LoginPresenter，那么就使用Mockito，让这个方法（provideLoginPresenter(...))被调用时，返回我们在测试类里面定义的mock loginPresenter。
 * <p>
 * 3.使用这个mock AppModule来构建一个Component，并且放到ComponentHolder里面去。
 * <p>
 * Created by liujunfeng on 2019/4/4.
 */
public class MockDaggerRule extends DaggerMockRule<MockComponent> {
    public MockDaggerRule() {
        //告诉DaggerMock要build什么样的Component，使用哪个module
        super(MockComponent.class, new MockModule());
        //告诉DaggerMock把build好的Component放到哪
        set(component -> MockUtils.setComponent(component));
    }
}