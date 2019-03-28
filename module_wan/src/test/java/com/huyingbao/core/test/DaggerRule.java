package com.huyingbao.core.test;

import com.huyingbao.module.wan.WanComponent;
import com.huyingbao.module.wan.module.WanModule;

import it.cosenonjaviste.daggermock.DaggerMockRule;

/**
 * Created by liujunfeng on 2019/3/28.
 */
public class DaggerRule extends DaggerMockRule<WanComponent> {
    public DaggerRule(Class<WanComponent> componentClass) {
        super(componentClass, new WanModule());
        //告诉DaggerMock把build好的Component放到哪
        set(new ComponentSetter<WanComponent>() {
            @Override
            public void setComponent(WanComponent appComponent) {
            }
        });
    }
}
