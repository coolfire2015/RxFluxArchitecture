package com.huyingbao.module.wan.module;

import com.huyingbao.module.wan.action.WanApi;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author liujunfeng
 * @date 2019/1/1
 */
@Singleton
@Component(modules = {MockModule.class})
public interface MockComponent {
    WanApi getWanApi();
}
