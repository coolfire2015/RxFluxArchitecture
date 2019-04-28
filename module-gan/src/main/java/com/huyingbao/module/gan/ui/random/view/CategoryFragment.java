package com.huyingbao.module.gan.ui.random.view;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.huyingbao.core.base.view.BaseFragment;
import com.huyingbao.core.common.R2;
import com.huyingbao.module.gan.R;
import com.huyingbao.module.gan.action.GanConstants;
import com.huyingbao.module.gan.ui.main.action.MainActionCreator;
import com.huyingbao.module.gan.ui.random.action.RandomAction;
import com.huyingbao.module.gan.ui.random.adapter.CategoryAdapter;
import com.huyingbao.module.gan.ui.random.store.RandomStore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 内容类型列表展示页面
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public class CategoryFragment extends BaseFragment<RandomStore> {
    @Inject
    MainActionCreator mActionCreator;
    @BindView(R2.id.rv_content)
    RecyclerView mRvContent;

    private List<String> mDataList;
    private CategoryAdapter mAdapter;

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.common_fragment_list;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        setTitle(R.string.gan_label_category, true);
        initRecyclerView();
        initAdapter();
        showData();
    }

    /**
     * 实例化RecyclerView,并设置adapter
     */
    private void initRecyclerView() {
        mRvContent.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvContent.setHasFixedSize(true);
        //硬件加速
        mRvContent.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mRvContent.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                mActionCreator.postLocalAction(RandomAction.TO_SHOW_DATA,
                        GanConstants.Key.CATEGORY, mDataList.get(position));
            }
        });
    }

    /**
     * 实例化adapter
     */
    private void initAdapter() {
        mDataList = new ArrayList();
        mAdapter = new CategoryAdapter(mDataList);
        //view设置适配器
        mRvContent.setAdapter(mAdapter);
    }

    /**
     * 显示数据
     */
    private void showData() {
        mDataList.addAll(Arrays.asList(getResources().getStringArray(R.array.gan_array_category)));
        mAdapter.notifyDataSetChanged();
    }
}
