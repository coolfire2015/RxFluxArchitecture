package com.huyingbao.module.history.ui.article.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.huyingbao.core.arch.view.FluxView
import com.huyingbao.core.base.common.fragment.BaseFragment
import com.huyingbao.module.common.utils.RecyclerItemClickListener
import com.huyingbao.module.history.R
import com.huyingbao.module.history.ui.article.action.ArticleActionCreator
import com.huyingbao.module.history.ui.article.adapter.BannerAdapter
import com.huyingbao.module.history.ui.article.store.ArticleStore
import com.schistoryg.smart.refresh.layout.SmartRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

/**
 * Created by liujunfeng on 2019/1/1.
 */
@AndroidEntryPoint
class BannerFragment : FluxView, BaseFragment() {
    @Inject
    lateinit var articleActionCreator: ArticleActionCreator

    private val rvContent by lazy {
        view?.findViewById<RecyclerView>(R.id.rv_content)
    }
    private val refreshLayout by lazy {
        view?.findViewById<SmartRefreshLayout>(R.id.rfl_content)
    }

    private val bannerAdapter by lazy {
        BannerAdapter(ArrayList())
    }

    companion object {
        fun newInstance() = BannerFragment()
    }

    override val store: ArticleStore by activityViewModels()

    override fun getLayoutId() = R.layout.common_fragment_list

    override fun afterCreate(savedInstanceState: Bundle?) {
//        setTitle(R.string.history_label_banner, true)
        initView()
    }

    /**
     * 初始化界面
     */
    private fun initView() {
        //设置RecyclerView
        rvContent?.apply {
            //RecyclerView设置适配器
            adapter = bannerAdapter
            //RecyclerView设置点击事件
            addOnItemTouchListener(
                RecyclerItemClickListener(context, this,
                    object : RecyclerItemClickListener.OnItemClickListener {
                        override fun onItemClick(view: View, position: Int) {
//                            context?.startWebActivity(bannerAdapter.getItem(position)?.url,
//                                    bannerAdapter.getItem(position)?.title)
                        }
                    })
            )
        }
        //显示数据
        store.bannerLiveData.observe(this, {
            bannerAdapter.setNewInstance(it)
        })
        articleActionCreator.getBannerList()
    }
}
