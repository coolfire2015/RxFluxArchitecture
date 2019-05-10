package com.huyingbao.core.image.strategy;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.huyingbao.core.image.ImageLoader;
import com.huyingbao.core.image.transform.CircleTransformation;
import com.huyingbao.core.image.transform.RotateTransformation;

public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy {

    @Override
    public void loadImage(Context context, ImageLoader img) {
        load(img, Glide.with(context).load(img.getResource()));
    }

    @Override
    public void loadImage(Activity activity, ImageLoader img) {
        load(img, Glide.with(activity).load(img.getResource()));
    }

    @Override
    public void loadImage(FragmentActivity fragmentActivity, ImageLoader img) {
        load(img, Glide.with(fragmentActivity).load(img.getResource()));
    }

    @Override
    public void loadImage(Fragment fragment, ImageLoader img) {
        load(img, Glide.with(fragment).load(img.getResource()));
    }

    private void load(ImageLoader imageLoader, RequestBuilder requestBuilder) {
        RequestOptions requestOptions = new RequestOptions();
        if (imageLoader.isFitCenter()) {
            // 图像居中,缩放到都能看到
            requestOptions.fitCenter();
        } else {
            // 图像居中,缩放到没有空白
            requestOptions.centerCrop();
        }
        if (imageLoader.getWidth() != 0 && imageLoader.getHeight() != 0) {
            requestOptions.override(imageLoader.getWidth(), imageLoader.getHeight());
        }

        if (imageLoader.getPlaceHolder() != 0) {
            requestOptions.placeholder(imageLoader.getPlaceHolder());
        }

        if (imageLoader.getErrorHolder() != 0) {
            requestOptions.error(imageLoader.getErrorHolder());
        }

        if (imageLoader.isNetImage()) {
            // 网络图片全部缓存
            requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        } else {
            // 本地图片,不需要缓存
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
            //增加签名,每次图片更改就不用修改url了，
            //直接修改version，相当于修改版本号了，版本号一改，那么glide就会去重新加载现在的图片！
            requestOptions.signature(new ObjectKey(System.currentTimeMillis() + ""));
        }
        if (imageLoader.isCircle()) {
            //圆形
            requestOptions.transform(new CircleTransformation());
        }

        if (imageLoader.getRotate() != 0f) {
            //旋转
            requestOptions.transform(new RotateTransformation(imageLoader.getRotate()));
        }

        requestBuilder.apply(requestOptions);

        if (imageLoader.getSizeMultiplier() != 0) {
            requestBuilder.thumbnail(imageLoader.getSizeMultiplier());
        }
        if (imageLoader.getRequestListener() != null) {
            requestBuilder.listener(imageLoader.getRequestListener());
        }
        requestBuilder.into(imageLoader.getImgView());
    }
}
