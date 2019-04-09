package com.huyingbao.module.wan.ui.friend.action

/**
 * Created by liujunfeng on 2019/1/1.
 */
interface FriendAction {

    /**
     * 获取友站列表
     */
    fun getFriendList()

    companion object {
        /**
         * 获取友站列表
         */
        val GET_FRIEND_LIST = "get_friend_list"
    }
}
