package com.huyingbao.core.image.strategy;

import android.app.Activity;
import android.content.Context;

import com.huyingbao.core.image.ImageLoader;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

/**
 * Picasso
 * Created by liujunfeng on 2017/1/1.
 */
public class PicassoImageLoaderStrategy implements BaseImageLoaderStrategy {
    @Override
    public void loadImage(Context ctx, ImageLoader img) {
    }

    @Override
    public void loadImage(Activity activity, ImageLoader img) {

    }

    @Override
    public void loadImage(FragmentActivity fragmentActivity, ImageLoader img) {

    }

    @Override
    public void loadImage(Fragment fragment, ImageLoader img) {

    }
}
