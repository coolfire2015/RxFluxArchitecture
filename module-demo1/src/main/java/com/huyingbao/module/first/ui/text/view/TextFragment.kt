package com.huyingbao.module.first.ui.text.view

import android.os.Bundle
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.module.first.R
import com.huyingbao.module.first.ui.text.store.TextStore

class TextFragment : BaseFluxFragment<TextStore>() {
    companion object {
        fun newInstance(): TextFragment {
            return TextFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_text
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
