package com.huyingbao.core.lifecycle;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huyingbao.core.RxFluxView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public abstract class RxFluxFragment extends Fragment implements RxFluxView {
    protected Context mContext;
    private Unbinder mUnbinder;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        setHasOptionsMenu(true);// fragment中创建菜单
        View rootView = inflater.inflate(getLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        afterCreate(savedInstanceState);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
