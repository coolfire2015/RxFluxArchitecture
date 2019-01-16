package com.huyingbao.core.common.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.arch.model.RxError;
import com.huyingbao.core.arch.view.RxFluxView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.ParameterizedType;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public abstract class CommonRxDialogFragment<T extends ViewModel> extends AppCompatDialogFragment implements CommonView, RxFluxView {
    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    private T mStore;
    private Unbinder mUnbinder;

    @Nullable
    @Override
    public T getRxStore() {
        if (mStore == null) {
            Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            mStore = ViewModelProviders.of(getActivity(), mViewModelFactory).get(tClass);
        }
        return mStore;
    }

    @Override
    public void onAttach(Context context) {
        //依赖注入
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //设置布局
        View rootView = LayoutInflater.from(getActivity()).inflate(getLayoutId(), null);
        //ButterKnife绑定
        mUnbinder = ButterKnife.bind(this, rootView);
        //创建AlertDialog
        AppCompatDialog dialog = new AppCompatDialog(getContext(), getTheme());
        dialog.setContentView(rootView);
        return dialog;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // view创建之后的操作
        afterCreate(savedInstanceState);
    }

    /**
     * 接收RxChange，粘性
     */
    @Override
    @Subscribe(sticky = true)
    public void onRxChanged(@NonNull RxChange rxChange) {
        //收到后，移除粘性通知
        EventBus.getDefault().removeStickyEvent(rxChange);
    }

    /**
     * 接收RxError，粘性
     * 该方法不经过store,
     * 由fragment直接处理
     */
    @Override
    @Subscribe(sticky = true)
    public void onRxError(@NonNull RxError error) {
        //收到后，移除粘性通知
        EventBus.getDefault().removeStickyEvent(error);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
