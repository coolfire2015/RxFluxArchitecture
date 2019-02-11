package com.huyingbao.module.gan.ui.random.store

import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.arch.store.RxActivityStore
import com.huyingbao.module.gan.action.GanConstants
import com.huyingbao.module.gan.action.GanResponse
import com.huyingbao.module.gan.ui.random.action.RandomAction
import com.huyingbao.module.gan.ui.random.model.Product

import org.greenrobot.eventbus.Subscribe

import javax.inject.Inject
import javax.inject.Singleton

import androidx.lifecycle.MutableLiveData

/**
 * 如果OS销毁app释放资源，用户数据不会丢失；
 * 当网络很差或者断网的时候app可以继续工作。
 *
 *
 * ViewModel的目的是获取并保存Activity或Fragment所必需的信息
 * Activity或Fragment应该能够观察到ViewModel中的变化
 *
 *
 * ViewModel对象在获取ViewModel时被限定为传递给ViewModelProvider的生命周期。
 * ViewModel保留在内存中，直到Activity销毁或Fragment分离之前。
 *
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
class RandomStore @Inject
internal constructor(rxDispatcher: RxDispatcher) : RxActivityStore(rxDispatcher) {
    val productListLiveData = MutableLiveData<List<Product>>()
    var nextRequestPage = 1//列表页数
    var category: String? = null

    /**
     * 当所有者Activity销毁时,框架调用ViewModel的onCleared（）方法，以便它可以清理资源。
     */
    override fun onCleared() {
        super.onCleared()
        nextRequestPage = 1
        category = null
        productListLiveData.value = null
    }

    /**
     * 接收RxAction
     * 处理RxAction携带的数据
     * 发送RxChange通知RxView
     *
     * @param rxAction
     */
    @Subscribe
    override fun onRxAction(rxAction: RxAction) {
        when (rxAction.tag) {
            RandomAction.GET_PRODUCT_LIST -> {
                val response = rxAction.getResponse<GanResponse<Product>>()
                if (productListLiveData.value == null) {
                    productListLiveData.setValue(response.results)
                } else {
                    productListLiveData.value!!.addAll(response.results!!)
                    productListLiveData.setValue(productListLiveData.value)
                }
                nextRequestPage++
            }
            RandomAction.TO_SHOW_DATA -> {
                onCleared()//跳转页面，先清除旧数据
                category = rxAction.get<String>(GanConstants.Key.CATEGORY)
                postChange(RxChange.newRxChange(rxAction.tag))
            }
        }
    }
}
