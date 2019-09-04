package com.huyingbao.module.first.ui.tmp.view

import android.os.Bundle
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.module.first.R
import com.huyingbao.module.first.ui.tmp.store.TmpStore

class TmpFragment : BaseFluxFragment<TmpStore>() {
    companion object {
        fun newInstance(): TmpFragment {
            return TmpFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_tmp
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
