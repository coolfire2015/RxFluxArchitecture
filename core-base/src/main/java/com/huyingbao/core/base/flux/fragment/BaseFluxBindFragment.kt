package com.huyingbao.core.base.flux.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.huyingbao.core.arch.utils.autoCleared


/**
 * 使用Dagger.Android，持有ViewModel，自动管理订阅
 *
 * 使用DataBinding的Fragment
 *
 * Created by liujunfeng on 2019/6/10.
 */
abstract class BaseFluxBindFragment<T : ViewModel, Y : ViewDataBinding> :
        BaseFluxFragment<T>() {
    /**
     * 根据Fragment动态清理和获取binding对象
     */
    var binding by autoCleared<Y?>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return binding?.root!!
    }
}
