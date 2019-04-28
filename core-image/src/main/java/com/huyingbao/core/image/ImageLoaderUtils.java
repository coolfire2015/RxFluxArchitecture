package com.huyingbao.core.image;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.fragment.app.Fragment;

import com.huyingbao.core.image.strategy.BaseImageLoaderStrategy;
import com.huyingbao.core.image.strategy.GlideImageLoaderStrategy;


/**
 * 图片加载工具类
 * Created by liujunfeng on 2017/1/1.
 */
public class ImageLoaderUtils {

    private static BaseImageLoaderStrategy mStrategy;

    private ImageLoaderUtils() {
        mStrategy = new GlideImageLoaderStrategy();
    }

    public static void setLoadImgStrategy(BaseImageLoaderStrategy strategy) {
        mStrategy = strategy;
    }

    public static void loadImage(Fragment fragment, ImageLoader imageLoader) {
        if (mStrategy == null) {
            mStrategy = new GlideImageLoaderStrategy();
        }
        mStrategy.loadImage(fragment, imageLoader);
    }

    public static void loadImage(Context context, ImageLoader imageLoader) {
        if (mStrategy == null) {
            mStrategy = new GlideImageLoaderStrategy();
        }
        mStrategy.loadImage(context, imageLoader);
    }

    /**
     * 无替换图片
     *
     * @param fragment
     * @param resource
     * @param imageView
     */
    public static void loadImage(Fragment fragment, Object resource, ImageView imageView) {
        ImageLoader.Builder builder = new ImageLoader.Builder();
        builder.imgView = imageView;
        builder.resource = resource;
        loadImage(fragment, builder.build());
    }

    /**
     * 无替换图片
     *
     * @param context
     * @param resource
     * @param imageView
     */
    public static void loadImage(Context context, Object resource, ImageView imageView) {
        ImageLoader.Builder builder = new ImageLoader.Builder();
        builder.imgView = imageView;
        builder.resource = resource;
        loadImage(context, builder.build());
    }

    /**
     * 有替换图片
     *
     * @param fragment
     * @param resource
     * @param placeHolder 图片加载失败的时候,显示的默认图片,是drawable中的图片
     * @param imageView
     */
    public static void loadImage(Fragment fragment, Object resource, @DrawableRes int placeHolder, ImageView imageView) {
        ImageLoader.Builder builder = new ImageLoader.Builder();
        builder.imgView = imageView;
        builder.resource = resource;
        builder.placeHolder = placeHolder;
        loadImage(fragment, builder.build());
    }

    /**
     * 有替换图片
     *
     * @param context
     * @param resource
     * @param placeHolder
     * @param imageView
     */
    public static void loadImage(Context context, Object resource, @DrawableRes int placeHolder, ImageView imageView) {
        ImageLoader.Builder builder = new ImageLoader.Builder();
        builder.imgView = imageView;
        builder.resource = resource;
        builder.placeHolder = placeHolder;
        loadImage(context, builder.build());
    }
}
