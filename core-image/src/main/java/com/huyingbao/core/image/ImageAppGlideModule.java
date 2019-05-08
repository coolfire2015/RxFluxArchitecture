package com.huyingbao.core.image;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

@GlideModule
public class ImageAppGlideModule extends AppGlideModule {
    @Override
    public boolean isManifestParsingEnabled() {
        //完全禁用清单解析
        return false;
    }
}