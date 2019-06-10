package com.huyingbao.module.github.ui.person.view

import android.os.Bundle
import com.huyingbao.core.base.fragment.BaseRxFragment
import com.huyingbao.module.github.R
import com.huyingbao.module.github.ui.person.store.PersonStore

class PersonFragment : BaseRxFragment<PersonStore>() {
    companion object {
        fun newInstance(): PersonFragment {
            return PersonFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.github_fragment_person
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
