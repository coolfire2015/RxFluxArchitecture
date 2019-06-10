package com.huyingbao.module.github.ui.code.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.huyingbao.core.base.activity.BaseRxFragActivity
import com.huyingbao.module.github.ui.code.store.CodeStore

class CodeActivity : BaseRxFragActivity<CodeStore>() {
    override fun createFragment(): Fragment? {
        return CodeFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
