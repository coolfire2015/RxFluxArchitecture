package com.huyingbao.module.github.ui.user.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.huyingbao.core.base.activity.BaseRxFragActivity
import com.huyingbao.module.github.ui.user.store.UserStore

/**
 * 登录用户个人信息模块
 *
 * Created by liujunfeng on 2019/6/10.
 */
class UserActivity : BaseRxFragActivity<UserStore>() {
    override fun createFragment(): Fragment {
        return UserFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}