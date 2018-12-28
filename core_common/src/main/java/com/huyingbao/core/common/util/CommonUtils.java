package com.huyingbao.core.common.util;

import android.app.Application;
import android.widget.Toast;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by liujunfeng on 2018/12/28.
 */
@Singleton
public class CommonUtils {

    private final Toast toast;

    @Inject
    CommonUtils(Application mApplication) {
        toast = Toast.makeText(mApplication, "", Toast.LENGTH_SHORT);
    }


    /**
     * 显示短暂的Toast
     *
     * @param text
     */
    public void showShortToast(String text) {
        toast.cancel();
        toast.setText(text);
        toast.show();
    }
}
