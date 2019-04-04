package com.huyingbao.module.wan.module;

import it.cosenonjaviste.daggermock.DaggerMockRule;

/**
 * Created by liujunfeng on 2019/4/4.
 */
public class MockDaggerRule extends DaggerMockRule<MockComponent> {
    public MockDaggerRule() {
        super(MockComponent.class, new MockModule());
        set(new DaggerMockRule.ComponentSetter<MockComponent>() {
            @Override
            public void setComponent(MockComponent component) {
                MockUtils.setComponent(component);
            }
        });
    }
}