package com.huyingbao.core.store;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

/**
 * 实现ViewModelProvider.Factory
 * Created by liujunfeng on 2017/12/7.
 */
@Singleton
public class RxStoreFactory implements ViewModelProvider.Factory {
    private final Map<Class<? extends RxStore>, Provider<RxStore>> creators;

    @Inject
    public RxStoreFactory(Map<Class<? extends RxStore>, Provider<RxStore>> creators) {
        this.creators = creators;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        Provider<? extends RxStore> creator = creators.get(modelClass);
        if (creator == null) {
            for (Map.Entry<Class<? extends RxStore>, Provider<RxStore>> entry : creators.entrySet()) {
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
            return (T) creator.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
