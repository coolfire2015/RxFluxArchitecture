package com.huyingbao.module.history.ui.article.store

import androidx.lifecycle.MutableLiveData
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.huyingbao.core.arch.model.Action
import com.huyingbao.core.arch.store.FluxStore
import com.huyingbao.module.common.app.CommonAppConstants
import com.huyingbao.module.history.app.WanAppDatabase
import com.huyingbao.module.history.model.Article
import com.huyingbao.module.history.model.Banner
import com.huyingbao.module.history.model.Page
import com.huyingbao.module.history.model.WanResponse
import com.huyingbao.module.history.ui.article.action.ArticleAction
import dagger.hilt.android.lifecycle.HiltViewModel
import org.greenrobot.eventbus.Subscribe
import java.util.*
import javax.inject.Inject

/**
 * Created by liujunfeng on 2019/1/1.
 */
@HiltViewModel
class ArticleStore @Inject constructor(
    private val historyAppDatabase: WanAppDatabase,
) : FluxStore() {
    /**
     * 默认起始页码
     */
    companion object {
        const val DEFAULT_PAGE = 0
    }

    /**
     * 列表页数
     */
    val pageLiveData by lazy {
        MutableLiveData<Int>().apply {
            value = DEFAULT_PAGE
        }
    }

    /**
     * 横幅数据
     */
    val bannerLiveData by lazy {
        MutableLiveData<ArrayList<Banner>>()
    }

    /**
     * 文章数据
     */
    val articleLiveData by lazy {
        historyAppDatabase.reposDao().getArticleList().toLiveData(
            config = Config(
                //每次加载多少数据
                pageSize = CommonAppConstants.Config.PAGE_SIZE,
                //距底部还有几条数据时，加载下一页数据
                prefetchDistance = 10,
                //第一次加载多少数据
                initialLoadSizeHint = 60,
                //是否启用占位符，若为true，则视为固定数量的item
                enablePlaceholders = true
            ),
            boundaryCallback = object : PagedList.BoundaryCallback<Article>() {
            })
    }

    /**
     * 当所有者Activity销毁时,框架调用ViewModel的onCleared（）方法，以便它可以清理资源。
     */
    override fun onCleared() {
        super.onCleared()
        pageLiveData.value = DEFAULT_PAGE
        bannerLiveData.value = null
    }

    /**
     * 接收BannerList数据
     */
    @Subscribe(tags = [ArticleAction.GET_BANNER_LIST])
    fun onGetBannerLiveData(rxAction: Action) {
        val bannerResponse = rxAction.getResponse<WanResponse<ArrayList<Banner>>>()
        bannerLiveData.value = bannerResponse!!.data
    }

    /**
     * 接收ArticleList数据，需要在新线程中，更新room数据库数据
     *
     * 不对外调用[ActivityStore.postChange]方法。
     */
    @Subscribe(tags = [ArticleAction.GET_ARTICLE_LIST])
    fun onGetArticleList(rxAction: Action) {
        val page = rxAction.get<Int>(CommonAppConstants.Key.PAGE) ?: DEFAULT_PAGE
        //如果是刷新，先清除数据库缓存
        if (page == DEFAULT_PAGE) {
            historyAppDatabase.reposDao().deleteAll()
        }
        //如果有数据，添加到数据库缓存中
        rxAction.getResponse<WanResponse<Page<Article>>>()?.data?.datas?.let {
            historyAppDatabase.reposDao().insertAll(it)
        }
        //主线程，更新分页索引
        pageLiveData.value = page + 1
    }
}
