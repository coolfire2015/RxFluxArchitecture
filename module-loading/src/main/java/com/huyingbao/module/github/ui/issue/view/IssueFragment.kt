package com.huyingbao.module.github.ui.issue.view

import android.os.Bundle
import com.huyingbao.core.base.fragment.BaseRxFragment
import com.huyingbao.module.github.R
import com.huyingbao.module.github.ui.issue.store.IssueStore

/**
 * 问题模块
 *
 * Created by liujunfeng on 2019/6/10.
 */
class IssueFragment : BaseRxFragment<IssueStore>() {
    companion object {
        fun newInstance(): IssueFragment {
            return IssueFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.github_fragment_issue
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
