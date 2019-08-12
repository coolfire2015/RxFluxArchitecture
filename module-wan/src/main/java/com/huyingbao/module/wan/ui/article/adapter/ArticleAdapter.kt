/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huyingbao.module.wan.ui.article.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.huyingbao.module.wan.R
import com.huyingbao.module.wan.ui.article.model.Article
import org.jetbrains.anko.find

/**
 *
 * 一个简单的PagedListAdapter，它将Article item 绑定到CardView。
 *
 * PagedListAdapter是一个[RecyclerView.Adapter]基类，它可以在RecyclerView中显示PagedLists的内容。
 * 它在用户滚动时请求新的页面，并通过计算后台线程上的列表差异来处理新的页面列表，
 * 并将最小的、有效的更新分派给RecyclerView，以确保最小的UI线程工作。
 *
 * 如果您想使用您自己的适配器基类，请尝试在适配器中使用[androidx.paging.AsyncPagedListDiffer]。
 *
 * @see androidx.paging.PagedListAdapter
 * @see androidx.paging.AsyncPagedListDiffer
 */
class ArticleAdapter : PagedListAdapter<Article, ArticleViewHolder>(diffCallback) {
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder = ArticleViewHolder(parent)

    public override fun getItem(position: Int): Article? = super.getItem(position)

    companion object {
        /**
         * 这个diff回调通知PagedListAdapter如何在新的PagedLists到达时计算列表差异。
         *
         * 当您使用添加删除一项时，PagedListAdapter使用diffCallback来检测与之前只有一个项目不同，因此它只需要动画和重新绑定一个视图。
         *
         * @see androidx.recyclerview.widget.DiffUtil
         */
        private val diffCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean = oldItem.id == newItem.id

            /**
             * 注意，在kotlin中，==检查数据类比较所有内容，但是在java中，通常您将实现object#equals，并使用它来比较对象内容。
             */
            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean = oldItem == newItem
        }
    }
}

/**
 * 一个简单ViewHolder。它还接受null项，因为数据可能在绑定之前没有被获取。
 */
class ArticleViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.wan_recycle_item, parent, false)) {
    /**
     * 如果尚未分页项，则它们可能为空。PagedListAdapter将在加载项时重新绑定ViewHolder。
     */
    fun bindTo(article: Article?) {
        article?.let {
            itemView.run {
                find<TextView>(R.id.tv_item_author).text = it.author
                find<TextView>(R.id.tv_item_title).text = Html.fromHtml(it.title)
                find<TextView>(R.id.tv_item_time).text = it.niceDate
                find<TextView>(R.id.tv_item_chapter).text = "${it.chapterName} / ${it.superChapterName}"
            }
        }
    }
}
