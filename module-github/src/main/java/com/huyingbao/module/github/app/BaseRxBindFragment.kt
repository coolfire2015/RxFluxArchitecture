package com.huyingbao.module.github.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

import com.huyingbao.core.base.fragment.BaseRxFragment
import com.huyingbao.module.github.utils.autoCleared

/**
 * 使用DataBinding的Fragment
 *
 * Created by liujunfeng on 2019/6/10.
 */
abstract class BaseRxBindFragment<T : ViewModel, Y : ViewDataBinding> : BaseRxFragment<T>() {
    /**
     * 根据Fragment动态清理和获取binding对象
     */
    var binding by autoCleared<Y?>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return binding?.root!!
    }
}
