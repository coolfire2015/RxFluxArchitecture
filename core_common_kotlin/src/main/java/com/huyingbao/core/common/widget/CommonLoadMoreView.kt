package com.huyingbao.core.common.widget

import com.chad.library.adapter.base.loadmore.LoadMoreView
import com.huyingbao.core.common.R

/**
 * Created by liujunfeng on 2018/12/21.
 */
class CommonLoadMoreView : LoadMoreView() {

    override fun getLayoutId(): Int {
        return R.layout.common_view_load_more
    }

    override fun getLoadingViewId(): Int {
        return R.id.load_more_loading_view
    }

    override fun getLoadFailViewId(): Int {
        return R.id.load_more_load_fail_view
    }

    override fun getLoadEndViewId(): Int {
        return R.id.load_more_load_end_view
    }
}
