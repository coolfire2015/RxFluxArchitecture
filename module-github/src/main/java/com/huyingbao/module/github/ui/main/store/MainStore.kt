package com.huyingbao.module.github.ui.main.store

import androidx.lifecycle.MutableLiveData
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.arch.store.RxActivityStore
import com.huyingbao.module.github.ui.login.model.User
import com.huyingbao.module.github.ui.main.action.MainAction
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainStore @Inject constructor(rxDispatcher: RxDispatcher?) : RxActivityStore(rxDispatcher) {

    val mUser= MutableLiveData<User>()

    override fun onCleared() {
        super.onCleared()
        mUser.value=null
    }

    @Subscribe(tags = [MainAction.GET_LOGIN_USER_INFO])
    fun onGetLoginUserInfo(rxAction: RxAction){
        mUser.value=rxAction.getResponse()
    }
}