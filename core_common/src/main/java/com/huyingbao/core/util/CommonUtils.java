package com.huyingbao.core.util;

import android.content.Context;
import android.widget.Toast;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public class CommonUtils {

    /**
     * 得到类的泛型类型
     *
     * @param aClass
     * @param <T>
     * @return
     */
    public static <T> Class<T> getGenericClass(Class aClass) {
        Class<T> genericClass = null;
        //返回直接继承的父类（包含泛型参数）
        Type genericSuperclass = aClass.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            try {
                genericClass = (Class<T>) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return genericClass;
    }

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
