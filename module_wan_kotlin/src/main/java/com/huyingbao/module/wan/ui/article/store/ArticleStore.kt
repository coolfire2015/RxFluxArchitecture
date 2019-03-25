package com.huyingbao.module.wan.ui.article.store

import androidx.lifecycle.MutableLiveData
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.arch.store.RxActivityStore
import com.huyingbao.module.wan.action.WanResponse
import com.huyingbao.module.wan.ui.article.action.ArticleAction
import com.huyingbao.module.wan.ui.article.model.Article
import com.huyingbao.module.wan.ui.article.model.Banner
import com.huyingbao.module.wan.ui.article.model.Page
import org.greenrobot.eventbus.Subscribe
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
class ArticleStore @Inject
internal constructor(rxDispatcher: RxDispatcher) : RxActivityStore(rxDispatcher) {
    val articleLiveData = MutableLiveData<ArrayList<Article>>()
    val bannerLiveData = MutableLiveData<ArrayList<Banner>>()
    var nextRequestPage = 1//列表页数

    /**
     * 当所有者Activity销毁时,框架调用ViewModel的onCleared（）方法，以便它可以清理资源。
     */
    override fun onCleared() {
        super.onCleared()
        nextRequestPage = 1
        articleLiveData.value = null
        bannerLiveData.value = null
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
            ArticleAction.GET_ARTICLE_LIST -> {
                val articleResponse = rxAction.getResponse<WanResponse<Page<Article>>>()
                if (articleLiveData.value == null) {
                    articleLiveData.setValue(articleResponse.data!!.datas)
                } else {
                    articleLiveData.value!!.addAll(articleResponse.data!!.datas!!)
                    articleLiveData.setValue(articleLiveData.value)
                }
                nextRequestPage++
            }
            ArticleAction.GET_BANNER_LIST -> {
                val bannerResponse = rxAction.getResponse<WanResponse<ArrayList<Banner>>>()
                bannerLiveData.setValue(bannerResponse.data)
            }
            ArticleAction.TO_BANNER, ArticleAction.TO_FRIEND, ArticleAction.TO_LOGIN -> postChange(RxChange.newInstance(rxAction))
        }
    }
}
