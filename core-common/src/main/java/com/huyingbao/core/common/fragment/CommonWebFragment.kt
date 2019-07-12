package com.huyingbao.core.common.fragment

import android.os.Bundle
import com.huyingbao.core.arch.view.RxSubscriberView
import com.huyingbao.core.base.BaseView
import com.huyingbao.core.base.fragment.BaseCommonFragment
import com.huyingbao.core.common.R
import com.huyingbao.core.common.module.CommonContants
import kotlinx.android.synthetic.main.common_fragment_web.*

/**
 * Created by liujunfeng on 2019/1/1.
 */
class CommonWebFragment : BaseCommonFragment(), BaseView, RxSubscriberView {
    companion object {
        fun newInstance(url: String?): CommonWebFragment {
            return CommonWebFragment()
                    .apply {
                        arguments = Bundle().apply {
                            putString(CommonContants.Key.URL, url)
                        }
                    }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.common_fragment_web
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        setTitle("Web", true)
        arguments?.let {
            if (it.containsKey(CommonContants.Key.URL)) {
                 web_content.loadUrl(it.getString(CommonContants.Key.URL))
            }
        }
    }
}
