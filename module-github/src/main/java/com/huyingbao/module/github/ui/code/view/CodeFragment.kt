package com.huyingbao.module.github.ui.code.view

import android.os.Bundle
import com.huyingbao.core.base.fragment.BaseRxFragment
import com.huyingbao.module.github.R
import com.huyingbao.module.github.ui.code.store.CodeStore

/**
 * 代码模块
 *
 * Created by liujunfeng on 2019/6/10.
 */
class CodeFragment : BaseRxFragment<CodeStore>() {
    companion object {
        fun newInstance(): CodeFragment {
            return CodeFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.github_fragment_code
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
