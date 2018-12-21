package com.huyingbao.module.gan.ui.category.view;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.huyingbao.core.arch.scope.ActivityScope;
import com.huyingbao.core.common.R2;
import com.huyingbao.core.common.view.CommonFragment;
import com.huyingbao.module.gan.R;
import com.huyingbao.module.gan.action.GanConstants;
import com.huyingbao.module.gan.ui.category.action.CategoryAction;
import com.huyingbao.module.gan.ui.category.action.CategoryActionCreator;
import com.huyingbao.module.gan.ui.category.adapter.CategoryAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * 内容类型列表展示页面
 * Created by liujunfeng on 2017/12/7.
 */
@ActivityScope
public class CategoryListFragment extends CommonFragment {
    @Inject
    CategoryActionCreator mActionCreator;
    @BindView(R2.id.rv_content)
    RecyclerView mRvContent;

    private List<String> mDataList = new ArrayList();
    private BaseQuickAdapter mAdapter = new CategoryAdapter(mDataList);

    @Inject
    public CategoryListFragment() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_list;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        initActionBar("首页");
        initRecyclerView();
        showData();
    }

    /**
     * 实例化RecyclerView,并设置adapter
     */
    private void initRecyclerView() {
        mRvContent.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvContent.setHasFixedSize(true);
        mRvContent.setAdapter(mAdapter);
        mRvContent.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//硬件加速
        mRvContent.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                mActionCreator.postLocalAction(CategoryAction.TO_RANDOM_LIST,
                        GanConstants.Key.CATEGORY, mDataList.get(position));
            }
        });
    }

    /**
     * 显示数据
     */
    private void showData() {
        mDataList.addAll(Arrays.asList(getResources().getStringArray(R.array.category_gan)));
        mAdapter.notifyDataSetChanged();
    }
}
