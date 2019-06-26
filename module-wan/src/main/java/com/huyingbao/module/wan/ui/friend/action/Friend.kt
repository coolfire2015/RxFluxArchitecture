package com.huyingbao.module.wan.ui.friend.action

import com.huyingbao.module.wan.app.WanResponse
import com.huyingbao.module.wan.ui.friend.model.WebSite
import io.reactivex.Observable
import retrofit2.http.GET
import java.util.*

/**
 * Created by liujunfeng on 2019/1/1.
 */
interface FriendAction {

    companion object {
        /**
         * 获取友站列表
         */
        const val GET_FRIEND_LIST = "get_friend_list"
    }

    /**
     * 获取友站列表
     */
    fun getFriendList()
}

interface FriendApi {
    /**
     * 获取友站列表
     */
    @GET("friend/json")
    fun getFriendList(): Observable<WanResponse<ArrayList<WebSite>>>
}
