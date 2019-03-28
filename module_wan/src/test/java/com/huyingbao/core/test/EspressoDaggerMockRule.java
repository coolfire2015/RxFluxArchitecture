package com.huyingbao.core.test;

import com.huyingbao.module.wan.WanApplication;
import com.huyingbao.module.wan.WanComponent;
import com.huyingbao.module.wan.module.WanModule;

import it.cosenonjaviste.daggermock.DaggerMockRule;

/**
 * Created by liujunfeng on 2019/3/28.
 */
public class EspressoDaggerMockRule extends DaggerMockRule<WanComponent> {
    public EspressoDaggerMockRule() {
        super(WanComponent.class, new WanModule());
        customizeBuilder(new BuilderCustomizer<WanComponent.Builder>() {
            @Override
            public WanComponent.Builder customize(WanComponent.Builder builder) {
                return builder.application(getApp());
            }
        });
        set(new DaggerMockRule.ComponentSetter<WanComponent>() {
            @Override
            public void setComponent(WanComponent component) {
                component.inject(getApp());
            }
        });
    }

    private static WanApplication getApp() {
        return (WanApplication) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
    }
}
