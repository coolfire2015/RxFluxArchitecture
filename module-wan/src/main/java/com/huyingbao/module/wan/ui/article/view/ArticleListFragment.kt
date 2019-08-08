package com.huyingbao.module.wan.ui.article.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.huyingbao.core.base.fragment.BaseRxFragment
import com.huyingbao.core.common.module.CommonContants
import com.huyingbao.module.wan.R
import com.huyingbao.module.wan.ui.article.action.ArticleAction
import com.huyingbao.module.wan.ui.article.action.ArticleActionCreator
import com.huyingbao.module.wan.ui.article.adapter.CheeseAdapter
import com.huyingbao.module.wan.ui.article.store.ArticleStore
import org.jetbrains.anko.find
import javax.inject.Inject

/**
 * Created by liujunfeng on 2019/1/1.
 */
class ArticleListFragment : BaseRxFragment<ArticleStore>() {
    @Inject
    lateinit var articleActionCreator: ArticleActionCreator

    private var articleAdapter: CheeseAdapter? = null

    companion object {
        fun newInstance(): ArticleListFragment {
            return ArticleListFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.common_fragment_list
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        setTitle(R.string.wan_label_article, true)
        initAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        //Menu布局文件名同界面布局文件名
        inflater.inflate(R.menu.wan_fragment_article_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when {
            item.itemId == R.id.menu_to_banner -> {
                articleActionCreator.postLocalChange(ArticleAction.TO_BANNER)
                true
            }
            item.itemId == R.id.menu_to_friend -> {
                articleActionCreator.postLocalChange(ArticleAction.TO_FRIEND)
                true
            }
            item.itemId == R.id.menu_to_gan -> {
                //跳转module-gan
                ARouter.getInstance().build(CommonContants.Address.RandomActivity).navigation()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * 实例化adapter
     */
    private fun initAdapter() {
        articleAdapter = CheeseAdapter()
        //view设置适配器
        view?.find<RecyclerView>(R.id.rv_content)?.adapter = articleAdapter
        //显示数据
        rxStore?.articleLiveData?.observe(this, Observer {
            articleAdapter?.submitList(it)
        })
    }
}
