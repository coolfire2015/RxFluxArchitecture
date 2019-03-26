package com.huyingbao.module.wan.ui.friend.action

/**
 * @author liujunfeng
 * @date 2019/1/1
 */
interface FriendAction {

    fun getFriendList()

    companion object {
        val GET_FRIEND_LIST = "get_friend_list"
    }
}
