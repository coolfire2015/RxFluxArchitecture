package com.huyingbao.module.common.ui.update.model

import android.app.Application
import android.content.pm.PackageManager
import android.text.TextUtils

data class AppBean(
        val binary: Binary,
        val build: String,
        val changelog: String,
        val direct_install_url: String,
        val installUrl: String,
        val install_url: String,
        val name: String,
        val update_url: String,
        val updated_at: Int,
        val version: String,
        val versionShort: String
) {
    var appUpdateState: AppUpdateState = AppUpdateState.DOWNLOAD
}

data class Binary(
        val fsize: Int
)

enum class AppUpdateState {
    /**
     * App已经安装，并且是最新版本
     */
    LATEST,
    /**
     * App已经下载最新的安装包，需要安装
     */
    INSTALL,
    /**
     * App没有安装，需要下载
     */
    DOWNLOAD,
    /**
     * App已经安装，需要更新
     */
    UPDATE
}

/**
 * 传入最新App版本号和已经下载的Apk文件地址，判断对应包名的App是需要下载，更新，安装，最新状态[AppUpdateState]
 *
 * @param build             最新App版本号
 * @param packageName       需要检测的App包名
 * @param archiveFilePath   AppApk文件在设备上的存储地址
 * @param application       当前AppApplication
 */
fun getAppState(
        build: String,
        packageName: String,
        archiveFilePath: String? = null,
        application: Application
): AppUpdateState {
    try {//最新Apk版本号
        val versionCode = Integer.valueOf(build)
        //本地是否已经有apk
        val archivePackageInfo = archiveFilePath?.let { application.packageManager.getPackageArchiveInfo(it, PackageManager.GET_ACTIVITIES) }
        //获取App是否已经安装
        val packageInfo = application.packageManager.getPackageInfo(packageName, PackageManager.GET_CONFIGURATIONS)
        //已经安装包名对应的App
        if (packageInfo != null) {
            //已安装是最新版本
            if (packageInfo.versionCode >= versionCode) return AppUpdateState.LATEST
            return if (archivePackageInfo != null) {
                //本地Apk文件是包名对应App的最新版本
                if (TextUtils.equals(archivePackageInfo.applicationInfo.packageName, packageName) && archivePackageInfo.versionCode >= versionCode) {
                    //直接安装本地Apk
                    AppUpdateState.INSTALL
                } else {
                    //需要更新到最新版本
                    AppUpdateState.UPDATE
                }
            } else {
                //需要更新到最新版本
                AppUpdateState.UPDATE
            }
        }
        //未安装包名对应的App
        return if (archivePackageInfo != null) {
            //本地Apk文件是包名对应App的最新版本
            if (TextUtils.equals(archivePackageInfo.applicationInfo.packageName, packageName) && archivePackageInfo.versionCode >= versionCode) {
                //直接安装本地Apk
                AppUpdateState.INSTALL
            } else {
                //需要下载最新版本
                AppUpdateState.DOWNLOAD
            }
        } else {
            //需要下载最新版本
            AppUpdateState.DOWNLOAD
        }
    } catch (e: Exception) {
        return AppUpdateState.DOWNLOAD
    }
}