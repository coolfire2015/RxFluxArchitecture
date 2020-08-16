package com.huyingbao.module.common.utils

import java.text.ParseException
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*

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
    /**
     * 格式化UTC时间（协调世界时）
     */
    fun formatUTCTime(utcTime: String): String {
        return try {
            //时间格式
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            //设置时区UTC
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            //格式化，转当地时区时间
            getNewsTimeStr(dateFormat.parse(utcTime))
//            //格式化，转当地时区时间
//            val date = dateFormat.parse(utcTime)
//            dateFormat.applyPattern("yyyy-MM-dd HH:mm:ss")
//            //默认时区
//            dateFormat.timeZone = TimeZone.getDefault()
//            dateFormat.format(date)
        } catch (e: ParseException) {
            ""
        }
    }

    /**
     * 时间戳转字符串
     */
    fun transToString(time: Long,
                      format: String = "yyyy-MM-dd HH:mm:ss"): String {
        return SimpleDateFormat(format).format(time)
    }

    /**
     * 字符串转时间戳
     */
    fun transToTimeStamp(date: String,
                         format: String = "yyyy-MM-dd HH:mm:ss"): Long {
        return SimpleDateFormat(format).parse(date, ParsePosition(0)).time
    }
}