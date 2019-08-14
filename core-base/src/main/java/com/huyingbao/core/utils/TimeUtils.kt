package com.huyingbao.core.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * 时间工具类
 *
 * Created by liujunfeng on 2019/1/1.
 */
object TimeUtils {

    private const val MILLIS_LIMIT = 1000.0

    private const val SECONDS_LIMIT = 60 * MILLIS_LIMIT

    private const val MINUTES_LIMIT = 60 * SECONDS_LIMIT

    private const val HOURS_LIMIT = 24 * MINUTES_LIMIT

    private const val DAYS_LIMIT = 30 * HOURS_LIMIT

    /**
     * 标准时间格式化
     */
    fun getDateStr(date: Date?): String {
        if (date?.toString() == null) {
            return ""
        } else if (date.toString().length < 10) {
            return date.toString()
        }
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(date).substring(0, 10)
    }

    /**
     * 获取时间格式化
     */
    fun getNewsTimeStr(date: Date?): String {
        if (date == null) {
            return ""
        }
        val subTime = Date().time - date.time
        return when {
            subTime < MILLIS_LIMIT -> "刚刚"
            subTime < SECONDS_LIMIT -> Math.round(subTime / MILLIS_LIMIT).toString() + " 秒前"
            subTime < MINUTES_LIMIT -> Math.round(subTime / SECONDS_LIMIT).toString() + " 分钟前"
            subTime < HOURS_LIMIT -> Math.round(subTime / MINUTES_LIMIT).toString() + " 小时前"
            subTime < DAYS_LIMIT -> Math.round(subTime / HOURS_LIMIT).toString() + " 天前"
            else -> getDateStr(date)
        }
    }
}