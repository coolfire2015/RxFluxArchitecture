package com.huyingbao.module.wan.ui.article.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huyingbao.module.wan.R
import com.huyingbao.module.wan.ui.article.model.Article

/**
 * Created by liujunfeng on 2019/1/1.
 */
class ArticleAdapter(data: List<Article>?) : BaseQuickAdapter<Article, BaseViewHolder>(R.layout.wan_recycle_item, data) {

    override fun convert(helper: BaseViewHolder, item: Article) {
        helper.setText(R.id.tv_item_name, item.author)
                .setText(R.id.tv_item_description, item.desc)
                .setText(R.id.tv_item_id, "GithubId" + item.id)
    }
}
