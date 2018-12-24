package com.huyingbao.core.common.widget;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.huyingbao.core.common.R;

/**
 * Created by liujunfeng on 2018/12/21.
 */
public class CommonLoadMoreView extends LoadMoreView {

    @Override
    public int getLayoutId() {
        return R.layout.view_load_more;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }
}
