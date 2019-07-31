package com.huyingbao.module.wan.ui.article.adapter

import android.text.Html
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huyingbao.module.wan.R
import com.huyingbao.module.wan.ui.article.model.Article

/**
 * Created by liujunfeng on 2019/1/1.
 */
class ArticleAdapter(data: List<Article>?)
    : BaseQuickAdapter<Article, BaseViewHolder>(R.layout.wan_recycle_item, data) {
    override fun convert(helper: BaseViewHolder, item: Article) {
        helper.setText(R.id.tv_item_author, item.author)
                .setText(R.id.tv_item_title, Html.fromHtml(item.title))
                .setText(R.id.tv_item_time, item.niceDate)
                .setText(R.id.tv_item_chapter, "${item.chapterName} / ${item.superChapterName}")
    }
}
