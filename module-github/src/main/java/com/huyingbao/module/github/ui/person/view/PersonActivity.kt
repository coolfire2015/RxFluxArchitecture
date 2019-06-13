package com.huyingbao.module.github.ui.person.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.huyingbao.core.base.activity.BaseRxFragActivity
import com.huyingbao.module.github.ui.person.store.PersonStore

/**
 * 用户模块
 *
 * Created by liujunfeng on 2019/6/10.
 */
class PersonActivity : BaseRxFragActivity<PersonStore>() {
    override fun createFragment(): Fragment? {
        return PersonFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
