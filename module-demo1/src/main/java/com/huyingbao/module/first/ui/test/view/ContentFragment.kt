package com.huyingbao.module.first.ui.test.view

import android.os.Bundle
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.core.base.setTitle
import com.huyingbao.module.common.app.CommonAppConstants
import com.huyingbao.module.first.R
import com.huyingbao.module.first.ui.test.action.TestAction
import com.huyingbao.module.first.ui.test.action.TestActionCreator
import com.huyingbao.module.first.ui.test.store.ContentStore
import kotlinx.android.synthetic.main.first_fragment_first.*
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject

class ContentFragment : BaseFluxFragment<ContentStore>() {
    private lateinit var tags: String
    @Inject
    lateinit var testActionCreator: TestActionCreator
    private var page: Int = 0

    companion object {
        fun newInstance(page: Int): ContentFragment {
            return ContentFragment().apply {
                arguments = Bundle().apply {
                    putInt(CommonAppConstants.Key.PAGE, page)
                }
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.first_fragment_first
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        setTitle(R.string.app_label_first, false)
        page = arguments?.getInt(CommonAppConstants.Key.PAGE, 0) ?: 0
        bt_get_hot_key.setOnClickListener {
            testActionCreator.getArticle(page)
        }
    }

    @Subscribe(tags = [TestAction.GET_ARTICLE])
    fun onGetArticle(rxChange: RxChange) {
        if (rxStore?.page == page){
            tv_hot_key.text = rxStore?.jsonObject.toString()

        }
    }
}
