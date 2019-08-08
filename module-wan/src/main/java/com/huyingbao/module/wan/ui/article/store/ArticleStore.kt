package com.huyingbao.module.wan.ui.article.store

import androidx.lifecycle.MutableLiveData
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.arch.store.RxActivityStore
import com.huyingbao.module.wan.app.WanResponse
import com.huyingbao.module.wan.db.WanAppDb
import com.huyingbao.module.wan.ui.article.action.ArticleAction
import com.huyingbao.module.wan.ui.article.action.ArticleActionCreator
import com.huyingbao.module.wan.ui.article.model.Article
import com.huyingbao.module.wan.ui.article.model.Banner
import com.huyingbao.module.wan.ui.article.model.Page
import com.orhanobut.logger.Logger
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
        private var wanAppDb: WanAppDb,
        private var articleActionCreator: ArticleActionCreator
) : RxActivityStore(rxDispatcher) {
    /**
     * 列表页数
     */
    private var nextRequestPage = 0

    val articleLiveData = wanAppDb.reposDao().getArticleList().toLiveData(
            config = Config(pageSize = 30, enablePlaceholders = true, initialLoadSizeHint = 20),
            initialLoadKey = 0,
            boundaryCallback = object : PagedList.BoundaryCallback<Article>() {
                override fun onZeroItemsLoaded() {
                    super.onZeroItemsLoaded()
                    nextRequestPage = 0
                    Logger.e("加载第一次")
                    articleActionCreator.getArticleList(nextRequestPage)
                }

                override fun onItemAtEndLoaded(itemAtEnd: Article) {
                    super.onItemAtEndLoaded(itemAtEnd)
                    Logger.e("加载第 $nextRequestPage 次")
                    articleActionCreator.getArticleList(nextRequestPage)
                }

                override fun onItemAtFrontLoaded(itemAtFront: Article) {
                    super.onItemAtFrontLoaded(itemAtFront)
                }
            }
    )

    val bannerLiveData: MutableLiveData<ArrayList<Banner>> by lazy { MutableLiveData<ArrayList<Banner>>() }


    /**
     * 当所有者Activity销毁时,框架调用ViewModel的onCleared（）方法，以便它可以清理资源。
     */
    override fun onCleared() {
        nextRequestPage = 0
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
            wanAppDb.reposDao().insertAll(it)
        }
        nextRequestPage++
    }
}
