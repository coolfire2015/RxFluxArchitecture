package com.huyingbao.module.github.ui.star.view

import android.os.Bundle
import com.huyingbao.core.base.fragment.BaseRxFragment
import com.huyingbao.module.github.R
import com.huyingbao.module.github.ui.star.store.StarStore

/**
 * 点赞
 *
 * Created by liujunfeng on 2019/6/10.
 */
class StarFragment : BaseRxFragment<StarStore>() {
    companion object {
        fun newInstance(): StarFragment {
            return StarFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.github_fragment_star
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
