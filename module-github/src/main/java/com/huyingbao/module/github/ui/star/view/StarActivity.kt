package com.huyingbao.module.github.ui.star.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.huyingbao.core.base.activity.BaseRxFragActivity
import com.huyingbao.module.github.ui.star.store.StarStore

/**
 * 点赞
 *
 * Created by liujunfeng on 2019/6/10.
 */
class StarActivity : BaseRxFragActivity<StarStore>() {
    override fun createFragment(): Fragment? {
        return StarFragment.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
