package com.huyingbao.core.common.module;

import com.huyingbao.core.arch.module.RxFluxModule;
import com.huyingbao.core.okhttp.HttpModule;
import com.huyingbao.core.progress.DownloadModule;

import dagger.Module;

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Module(includes = {RxFluxModule.class, HttpModule.class, DownloadModule.class})
public class CommonModule {
}

