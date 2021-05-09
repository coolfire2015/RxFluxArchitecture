//package com.huyingbao.module.common.app
//
//import android.app.Application
//import android.content.Context
//import androidx.startup.Initializer
//
//class CommonStartUp : Initializer<CommonAppStore> {
//    override fun create(context: Context): CommonAppStore {
//        return CommonAppStore(context as Application)
//    }
//
//    override fun dependencies(): List<Class<out Initializer<*>>> {
//        return emptyList()
//    }
//}