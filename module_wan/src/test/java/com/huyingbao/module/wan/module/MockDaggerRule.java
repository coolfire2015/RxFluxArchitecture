package com.huyingbao.module.wan.module;

import it.cosenonjaviste.daggermock.DaggerMockRule;

/**
 * Created by liujunfeng on 2019/4/4.
 */
public class MockDaggerRule extends DaggerMockRule<MockComponent> {
    public MockDaggerRule() {
        super(MockComponent.class, new MockModule());
        set(component -> MockUtils.setComponent(component));
    }
}