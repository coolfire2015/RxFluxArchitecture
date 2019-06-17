package com.huyingbao.module.github.ui.main.action

/**
 * 主页模块
 *
 * Created by liujunfeng on 2019/6/10.
 */
interface MainAction {


    /**
     * 发送用户反馈
     */
    fun feedback(editContent: String)

    /**
     * 获取最新动态
     */
    fun getNewsEvent(user: String, page: Int)

    companion object {
        /**
         * 发送用户反馈
         */
        const val FEED_BACK = "feedback"

        /**
         * 获取最新动态
         */
        const val GET_NEWS_EVENT = "getNewsEvent"
    }
}