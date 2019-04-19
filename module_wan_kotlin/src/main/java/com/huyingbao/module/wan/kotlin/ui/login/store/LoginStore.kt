package com.huyingbao.module.wan.kotlin.ui.login.store

import androidx.lifecycle.MutableLiveData
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.arch.store.RxActivityStore
import com.huyingbao.module.wan.kotlin.action.WanResponse
import com.huyingbao.module.wan.kotlin.ui.login.action.LoginAction
import com.huyingbao.module.wan.kotlin.ui.login.model.User
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
class LoginStore @Inject
internal constructor(rxDispatcher: RxDispatcher) : RxActivityStore(rxDispatcher) {
    val intervalLiveData = MutableLiveData<String>()
    private var mUser: WanResponse<User>? = null

    override fun onCleared() {
        super.onCleared()
        intervalLiveData.value = null
    }

    @Subscribe(tags = [LoginAction.LOGIN])
    fun onLogin(rxAction: RxAction) {
        mUser = rxAction.getResponse<WanResponse<User>>()
        postChange(RxChange.newInstance(rxAction))
    }

    @Subscribe(tags = [LoginAction.GET_IDENTIFY])
    fun setIntervalLiveData(rxAction: RxAction) {
        intervalLiveData.value = rxAction.getResponse<Any>().toString() + ""
    }
}
