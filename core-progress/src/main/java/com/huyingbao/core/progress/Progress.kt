package com.huyingbao.core.progress

import org.greenrobot.eventbus.EventBusEvent

/**
 * Created by liujunfeng on 2019/1/1.
 */
class RxProgress private constructor(
        tag: String
) : EventBusEvent(tag) {
    /**
     * 当前已上传或下载的总长度
     */
    var currentLength: Long = 0

    /**
     * 数据总长度
     */
    var contentLength: Long = 0

    /**
     * 本次调用距离上一次被调用所间隔的时间(毫秒)
     */
    var intervalTime: Long = 0

    /**
     * 本次调用距离上一次被调用的间隔时间内上传或下载的byte长度
     */
    var eachLength: Long = 0

    /**
     * 获取百分比
     *
     * @return
     */
    val percent: Int
        get() = if (currentLength <= 0 || contentLength <= 0) 0 else (100 * currentLength / contentLength).toInt()

    /**
     * 获取上传或下载网络速度,单位为byte/s
     *
     * @return
     */
    val speed: Long
        get() = if (eachLength <= 0 || intervalTime <= 0) 0 else eachLength * 1000 / intervalTime

    companion object {
        const val TAG = "tag"
        fun newInstance(tag: String): Progress {
            return Progress(tag)
        }
    }
}