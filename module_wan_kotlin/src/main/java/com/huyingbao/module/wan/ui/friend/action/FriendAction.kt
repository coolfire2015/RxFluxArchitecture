package com.huyingbao.module.wan.ui.friend.action

/**
 * Created by liujunfeng on 2019/1/1.
 */
interface FriendAction {

    /**
     * ��ȡ��վ�б�
     */
    fun getFriendList()

    companion object {
        /**
         * ��ȡ��վ�б�
         */
        val GET_FRIEND_LIST = "get_friend_list"
    }
}
