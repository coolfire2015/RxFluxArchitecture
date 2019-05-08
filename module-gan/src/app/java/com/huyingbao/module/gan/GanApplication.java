package com.huyingbao.module.gan;


import com.huyingbao.core.annotations.RxAppBody;
import com.huyingbao.core.base.BaseApp;

import org.greenrobot.eventbus.EventBus;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * Created by liujunfeng on 2019/1/1.
 */
@RxAppBody
public class GanApplication extends BaseApp {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerGanComponent.builder().application(this).build();
    }
}
