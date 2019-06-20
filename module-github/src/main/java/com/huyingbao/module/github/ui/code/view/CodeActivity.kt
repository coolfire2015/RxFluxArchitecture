package com.huyingbao.module.github.ui.code.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.huyingbao.core.base.activity.BaseRxFragActivity
import com.huyingbao.core.common.module.CommonContants
import com.huyingbao.module.github.ui.code.store.CodeStore

/**
 * 代码模块
 *
 * Created by liujunfeng on 2019/6/10.
 */
@Route(path = CommonContants.Address.CodeActivity)
class CodeActivity : BaseRxFragActivity<CodeStore>() {
    override fun createFragment(): Fragment? {
        return CodeFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
