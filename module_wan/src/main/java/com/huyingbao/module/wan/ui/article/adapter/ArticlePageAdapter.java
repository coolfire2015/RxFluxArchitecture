package com.huyingbao.module.wan.ui.article.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huyingbao.module.wan.R;
import com.huyingbao.module.wan.ui.article.model.Article;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by liujunfeng on 2019/1/8.
 */
public class ArticlePageAdapter extends PagedListAdapter<Article, ArticlePageAdapter.ArticleViewHolder> {
    protected ArticlePageAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        return new ArticleViewHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    /**
     * Created by liujunfeng on 2019/1/8.
     */
    class ArticleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_item_name)
        TextView mTvItemName;
        @BindView(R.id.tv_item_description)
        TextView mTvItemDescription;
        @BindView(R.id.tv_item_id)
        TextView mTvItemId;

        public ArticleViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.wan_recycle_item, parent, false));
        }

        public void bind(Article article) {
            mTvItemId.setText(article.getId() + "");
            mTvItemDescription.setText(article.getDesc());
            mTvItemName.setText(article.getTitle());
        }
    }

    private static DiffUtil.ItemCallback<Article> DIFF_CALLBACK = new DiffUtil.ItemCallback<Article>() {
        // The ID property identifies when items are the same.
        @Override
        public boolean areItemsTheSame(Article oldItem, Article newItem) {
            return oldItem.getId() == newItem.getId();
        }

        // Use Object.equals() to know when an item's content changes.
        // Implement equals(), or write custom data comparison logic here.
        @Override
        public boolean areContentsTheSame(Article oldItem, Article newItem) {
            return oldItem.equals(newItem);
        }
    };

}
