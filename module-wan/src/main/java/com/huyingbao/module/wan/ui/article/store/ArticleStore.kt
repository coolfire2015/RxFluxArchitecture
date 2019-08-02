package com.huyingbao.module.wan.ui.article.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.arch.store.RxActivityStore
import com.huyingbao.module.wan.app.WanResponse
import com.huyingbao.module.wan.database.WanAppDatabase
import com.huyingbao.module.wan.ui.article.action.ArticleAction
import com.huyingbao.module.wan.ui.article.model.Article
import com.huyingbao.module.wan.ui.article.model.Banner
import com.huyingbao.module.wan.ui.article.model.Page
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
class ArticleStore @Inject constructor(
        rxDispatcher: RxDispatcher,
        private var wanAppDatabase: WanAppDatabase
) : RxActivityStore(rxDispatcher) {
    val articleLiveData: LiveData<List<Article>> by lazy { wanAppDatabase.reposDao().getArticleListLiveData() }
    val bannerLiveData: MutableLiveData<ArrayList<Banner>> by lazy { MutableLiveData<ArrayList<Banner>>() }
    /**
     * 列表页数
     */
    var nextRequestPage = 1

    /**
     * 当所有者Activity销毁时,框架调用ViewModel的onCleared（）方法，以便它可以清理资源。
     */
    override fun onCleared() {
        nextRequestPage = 1
        bannerLiveData.value = null
    }

    /**
     * 接收BannerList数据
     */
    @Subscribe(tags = [ArticleAction.GET_BANNER_LIST])
    fun onGetBannerLiveData(rxAction: RxAction) {
        val bannerResponse = rxAction.getResponse<WanResponse<ArrayList<Banner>>>()
        bannerLiveData.value = bannerResponse!!.data
    }

    /**
     * 接收ArticleList数据
     */
    @Subscribe(tags = [ArticleAction.GET_ARTICLE_LIST], threadMode = ThreadMode.POSTING)
    fun onGetArticleLiveData(rxAction: RxAction) {
        rxAction.getResponse<WanResponse<Page<Article>>>()?.data?.datas?.let {
            wanAppDatabase.reposDao().insertAll(it)
        }
        nextRequestPage++
    }
}
