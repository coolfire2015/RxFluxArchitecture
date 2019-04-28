package com.huyingbao.core.image.strategy;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.huyingbao.core.image.ImageLoader;


/**
 * Created by liujunfeng on 2017/1/1.
 */
public interface BaseImageLoaderStrategy {
    void loadImage(Context context, ImageLoader img);

    void loadImage(Activity activity, ImageLoader img);

    void loadImage(FragmentActivity fragmentActivity, ImageLoader img);

    void loadImage(Fragment fragment, ImageLoader img);
}
