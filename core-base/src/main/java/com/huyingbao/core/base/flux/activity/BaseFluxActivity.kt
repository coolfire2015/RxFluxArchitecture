package com.huyingbao.core.base.flux.activity

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.huyingbao.core.arch.store.RxActivityStore
import com.huyingbao.core.arch.view.RxFluxActivity
import com.huyingbao.core.base.BaseActionCreator
import com.huyingbao.core.base.BaseView
import javax.inject.Inject

/**
 * 使用Dagger.Android，持有ViewModel，自动管理订阅
 *
 * 带有[Toolbar]的Activity父类
 *
 * Created by liujunfeng on 2019/1/1.
 */
abstract class BaseFluxActivity<T : RxActivityStore> :
        RxFluxActivity<T>(),
        BaseView {
    @Inject
    lateinit var baseActionCreator: BaseActionCreator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        //使用Toolbar取代原本的actionbar
        window.decorView.findViewById<Toolbar>(getToolbarId())?.let { setSupportActionBar(it) }
        afterCreate(savedInstanceState)
    }
}
