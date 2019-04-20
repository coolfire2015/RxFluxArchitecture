package com.huyingbao.module.gan.ui.main.view;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.base.rxview.BaseRxActivity;
import com.huyingbao.module.gan.ui.main.action.MainAction;
import com.huyingbao.module.gan.ui.main.store.MainStore;
import com.huyingbao.module.gan.ui.random.view.RandomActivity;

import org.greenrobot.eventbus.Subscribe;

import androidx.fragment.app.Fragment;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public class MainActivity extends BaseRxActivity<MainStore> {
    @Override
    protected Fragment createFragment() {
        return MainFragment.newInstance();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
    }

    @Subscribe(tags = {MainAction.TO_WAN_MODULE})
    public void toWanModule(RxChange rxChange) {
        ARouter.getInstance().build("/wan/ArticleActivity").navigation();
    }

    @Subscribe(tags = {MainAction.TO_GAN_MODULE})
    public void toGanModule(RxChange rxChange) {
        startActivity(new Intent(this, RandomActivity.class));
    }

    @Subscribe(tags = {MainAction.TO_WAN_KOTLIN_MODULE})
    public void toWanKotlinModule(RxChange rxChange){
        ARouter.getInstance().build("/wankotlin/ArticleActivity").navigation();
    }
}
