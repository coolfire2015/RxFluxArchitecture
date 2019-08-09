package com.huyingbao.core.common.utils

import android.content.Context
import android.text.TextUtils
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException


object AndroidUtils {
    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    fun getProcessName(pid: Int): String? {
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(FileReader("/proc/$pid/cmdline"))
            var processName = reader.readLine()
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim { it <= ' ' }
            }
            return processName
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        } finally {
            try {
                reader?.close()
            } catch (exception: IOException) {
                exception.printStackTrace()
            }
        }
        return null
    }

    /**
     * 获取当前Application的Label
     */
    fun getApplicationLabel(context: Context): String? =
            context.resources.getString(context.packageManager.getApplicationInfo(context.packageName, 0).labelRes)
}
