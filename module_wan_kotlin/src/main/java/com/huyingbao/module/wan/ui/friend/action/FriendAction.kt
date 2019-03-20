package com.huyingbao.module.wan.ui.friend.action

/**
 * Created by liujunfeng on 2018/12/26.
 */
interface FriendAction {

    fun getFriendList()

    companion object {
        val GET_FRIEND_LIST = "get_friend_list"
    }
}
