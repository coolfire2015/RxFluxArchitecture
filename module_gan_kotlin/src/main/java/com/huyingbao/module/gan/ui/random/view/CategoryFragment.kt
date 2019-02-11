package com.huyingbao.module.gan.ui.random.view

import android.os.Bundle
import android.view.View

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.core.common.R2
import com.huyingbao.core.common.view.CommonRxFragment
import com.huyingbao.module.gan.R
import com.huyingbao.module.gan.action.GanConstants
import com.huyingbao.module.gan.ui.main.action.MainActionCreator
import com.huyingbao.module.gan.ui.random.action.RandomAction
import com.huyingbao.module.gan.ui.random.adapter.CategoryAdapter
import com.huyingbao.module.gan.ui.random.store.RandomStore

import java.util.ArrayList
import java.util.Arrays

import javax.inject.Inject

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView

/**
 * 内容类型列表展示页面
 * Created by liujunfeng on 2019/1/1.
 */
@ActivityScope
class CategoryFragment @Inject
constructor() : CommonRxFragment<RandomStore>() {
    @Inject
    internal var mActionCreator: MainActionCreator? = null
    @BindView(R2.id.rv_content)
    internal var mRvContent: RecyclerView? = null

    private var mDataList: MutableList<String>? = null
    private var mAdapter: BaseQuickAdapter<*, *>? = null

    override fun getLayoutId(): Int {
        return R.layout.common_fragment_base_list
    }

    override fun afterCreate(savedInstanceState: Bundle) {
        setTitle(R.string.gan_label_category, true)
        initRecyclerView()
        initAdapter()
        showData()
    }

    /**
     * 实例化RecyclerView,并设置adapter
     */
    private fun initRecyclerView() {
        mRvContent!!.layoutManager = LinearLayoutManager(activity)
        mRvContent!!.setHasFixedSize(true)
        mRvContent!!.setLayerType(View.LAYER_TYPE_SOFTWARE, null)//硬件加速
        mRvContent!!.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                mActionCreator!!.postLocalAction(RandomAction.TO_SHOW_DATA,
                        GanConstants.Key.CATEGORY, mDataList!![position])
            }
        })
    }

    /**
     * 实例化adapter
     */
    private fun initAdapter() {
        mDataList = ArrayList()
        mAdapter = CategoryAdapter(mDataList)
        //view设置适配器
        mRvContent!!.adapter = mAdapter
    }

    /**
     * 显示数据
     */
    private fun showData() {
        mDataList!!.addAll(Arrays.asList(*resources.getStringArray(R.array.gan_array_category)))
        mAdapter!!.notifyDataSetChanged()
    }
}
