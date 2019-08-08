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
 * A simple PagedListAdapter that binds Cheese items into CardViews.
 * <p>
 * PagedListAdapter is a RecyclerView.Adapter base class which can present the content of PagedLists
 * in a RecyclerView. It requests new pages as the user scrolls, and handles new PagedLists by
 * computing list differences on a background thread, and dispatching minimal, efficient updates to
 * the RecyclerView to ensure minimal UI thread work.
 * <p>
 * If you want to use your own Adapter base class, try using a PagedListAdapterHelper inside your
 * adapter instead.
 *
 * 一个简单的PagedListAdapter，它将奶酪项目绑定到cardview。
 *
 * PagedListAdapter是一个clerview。适配器基类，它可以在repeatclerview中显示PagedLists *的内容。
 * 它在用户滚动时请求新的页面，并通过计算后台线程上的列表差异来处理新的页面列表，并将最小的、有效的更新分派给clerview，以确保最小的UI线程工作。
 *
 * 如果您想使用您自己的适配器基类，请尝试在您的*适配器中使用PagedListAdapterHelper。
 *
 * @see androidx.paging.PagedListAdapter
 * @see androidx.paging.AsyncPagedListDiffer
 */
class CheeseAdapter : PagedListAdapter<Article, CheeseViewHolder>(diffCallback) {
    override fun onBindViewHolder(holder: CheeseViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheeseViewHolder =
            CheeseViewHolder(parent)

    companion object {
        /**
         * This diff callback informs the PagedListAdapter how to compute list differences when new
         * PagedLists arrive.
         * <p>
         * When you add a Cheese with the 'Add' button, the PagedListAdapter uses diffCallback to
         * detect there's only a single item difference from before, so it only needs to animate and
         * rebind a single view.
         *
         * @see androidx.recyclerview.widget.DiffUtil
         */
        private val diffCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
                    oldItem.id == newItem.id

            /**
             * Note that in kotlin, == checking on data classes compares all contents, but in Java,
             * typically you'll implement Object#equals, and use it to compare object contents.
             */
            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
                    oldItem == newItem
        }
    }
}

/**
 * 一个简单ViewHolder。它还接受null项，因为数据可能在绑定之前没有被获取。
 */
class CheeseViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.wan_recycle_item, parent, false)) {

    private val author = itemView.find<TextView>(R.id.tv_item_author)
    private val title = itemView.find<TextView>(R.id.tv_item_title)
    private val time = itemView.find<TextView>(R.id.tv_item_time)
    private val chapter = itemView.find<TextView>(R.id.tv_item_chapter)
    var article: Article? = null

    /**
     * 如果尚未分页项，则它们可能为空。PagedListAdapter将在加载项时重新绑定ViewHolder。
     */
    fun bindTo(article: Article?) {
        this.article = article
        article?.let {
            author.text = it.author
            title.text = Html.fromHtml(it.title)
            time.text = it.niceDate
            chapter.text = "${it.chapterName} / ${it.superChapterName}"
        }
    }
}
