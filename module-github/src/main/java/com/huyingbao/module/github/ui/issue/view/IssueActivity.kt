package com.huyingbao.module.github.ui.issue.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.huyingbao.core.base.activity.BaseRxFragActivity
import com.huyingbao.module.github.ui.issue.store.IssueStore

/**
 * 问题模块
 *
 * Created by liujunfeng on 2019/6/10.
 */
class IssueActivity : BaseRxFragActivity<IssueStore>() {
    override fun createFragment(): Fragment? {
        return IssueFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
