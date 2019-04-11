package com.huyingbao.core.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public class CommonUtils {

    /**
     * 显示短暂的Toast
     *
     * @param context
     * @param text
     */
    public static void showShortToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
