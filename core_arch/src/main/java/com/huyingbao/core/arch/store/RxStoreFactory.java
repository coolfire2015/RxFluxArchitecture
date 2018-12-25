package com.huyingbao.core.arch.store;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * 实现ViewModelProvider.Factory
 * 提供ViewModel缓存的实例
 * 通过Dagger2将Map直接注入，
 * 通过Key直接获取到对应的ViewModel
 * Created by liujunfeng on 2017/12/7.
 */
@Singleton
public class RxStoreFactory implements ViewModelProvider.Factory {
    private final Map<Class<? extends RxStoreForActivity>, Provider<RxStoreForActivity>> creators;

    @Inject
    RxStoreFactory(Map<Class<? extends RxStoreForActivity>, Provider<RxStoreForActivity>> creators) {
        this.creators = creators;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        Provider<? extends RxStoreForActivity> creator = creators.get(modelClass);
        //通过class找到相应ViewModel的Provider
        if (creator == null) {
            for (Map.Entry<Class<? extends RxStoreForActivity>, Provider<RxStoreForActivity>> entry : creators.entrySet()) {
                if (modelClass.isAssignableFrom(entry.getKey())) {
                    creator = entry.getValue();
                    break;
                }
            }
        }
        if (creator == null) {
            throw new IllegalArgumentException("unknown model class " + modelClass);
        }
        try {
            //通过get()方法获取到ViewModel
            return (T) creator.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
