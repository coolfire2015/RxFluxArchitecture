package com.huyingbao.module.first.ui.test.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.module.first.R
import com.huyingbao.module.first.ui.test.store.TestStore
import kotlinx.android.synthetic.main.first_fragment_test.*

class TestFragment : BaseFluxFragment<TestStore>() {
    companion object {
        fun newInstance(): TestFragment {
            return TestFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.first_fragment_test
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        initViewPager()
    }

    private fun initViewPager() {
        val categoryArray = resources.getStringArray(R.array.first_array_category)
        view_pager_content.offscreenPageLimit = 7
        view_pager_content.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment {
                return ContentFragment.newInstance(position)
            }

            override fun getItemCount(): Int {
                return categoryArray.size
            }
        }
        TabLayoutMediator(tab_layout_top, view_pager_content) { tab, position ->
            tab.text = categoryArray[position]
        }.attach()
    }
}
