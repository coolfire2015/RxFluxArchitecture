package com.huyingbao.module.first.ui.text.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.huyingbao.core.base.flux.activity.BaseFluxFragActivity
import com.huyingbao.module.first.ui.text.store.TextStore

class TextActivity : BaseFluxFragActivity<TextStore>() {
    override fun createFragment(): Fragment? {
        return TextFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
