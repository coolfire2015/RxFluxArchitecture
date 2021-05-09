package com.huyingbao.core.base.common.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.huyingbao.core.base.BaseView

/**
 * 不使用Dagger.Android，不持有ViewModel，不自动管理订阅
 *
 * Fragment可创建Menu
 *
 * Created by liujunfeng on 2019/1/1.
 */
abstract class BaseFragment :
        Fragment(),
        BaseView {
    var backAble: Boolean = false
    var title: CharSequence? = null

    /**
     * 告诉FragmentManager:其管理的fragment应接收onCreateOptionsMenu(...)方法的调用指令，fragment中创建Menu。
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        afterCreate(savedInstanceState)
    }

    /**
     * 隐藏状态转变，
     * 从隐藏转为非隐藏的时候调用，当前页面显示时，显示对应的标题
     */
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
//            setTitle(title, backAble)
        }
    }
}