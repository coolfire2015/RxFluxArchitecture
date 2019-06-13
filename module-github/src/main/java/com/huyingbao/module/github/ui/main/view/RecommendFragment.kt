package com.huyingbao.module.github.ui.main.view

import android.os.Bundle
import com.huyingbao.core.base.fragment.BaseRxFragment
import com.huyingbao.module.github.R
import com.huyingbao.module.github.ui.main.store.MainStore

/**
 * 推荐页面
 *
 * Created by liujunfeng on 2019/6/10.
 */
class RecommendFragment : BaseRxFragment<MainStore>() {
    companion object {
        fun newInstance(): RecommendFragment {
            return RecommendFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.github_fragment_recommend
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}