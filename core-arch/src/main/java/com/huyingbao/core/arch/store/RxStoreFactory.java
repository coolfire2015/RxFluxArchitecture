package com.huyingbao.core.arch.store;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

/**
 * 实现{@link ViewModelProvider.Factory}，提供过依赖注入获取{@link RxStore} Map集合， 缓存并对外提供{@link RxStore}实例
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */

@Singleton
public class RxStoreFactory implements ViewModelProvider.Factory {
    private final Map<Class<? extends ViewModel>, Provider<ViewModel>> mClassProviderMap;

    @Inject
    RxStoreFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> mClassProviderMap) {
        this.mClassProviderMap = mClassProviderMap;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        Provider<? extends ViewModel> viewModelProvider = mClassProviderMap.get(modelClass);
        //通过class找到相应ViewModel的Provider
        if (viewModelProvider == null) {
            for (Map.Entry<Class<? extends ViewModel>, Provider<ViewModel>> entry : mClassProviderMap.entrySet()) {
                if (modelClass.isAssignableFrom(entry.getKey())) {
                    viewModelProvider = entry.getValue();
                    break;
                }
            }
        }
        if (viewModelProvider == null) {
            throw new IllegalArgumentException("unknown model class " + modelClass);
        }
        try {
            //通过get()方法获取到ViewModel
            return (T) viewModelProvider.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
