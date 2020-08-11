package com.huyingbao.module.app

import com.huyingbao.core.annotations.AppOwner
import com.huyingbao.core.base.BaseApp

import dagger.hilt.android.HiltAndroidApp

/**
 * 主Application
 *
 * Created by liujunfeng on 2019/1/1.
 */
@AppOwner
@HiltAndroidApp
class SimpleApplication : BaseApp() {
}
