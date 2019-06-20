package com.huyingbao.core.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.huyingbao.core.arch.view.RxFluxFragment
import com.huyingbao.core.base.BaseView
import com.huyingbao.core.common.R
import com.huyingbao.core.common.action.CommonActionCreator
import javax.inject.Inject

/**
 * Created by liujunfeng on 2019/1/1.
 */
abstract class BaseRxFragment<T : ViewModel> : RxFluxFragment<T>(), BaseView {
    @Inject
    lateinit var commonActionCreator: CommonActionCreator

    private var mBackAble: Boolean = false
    private var mTitle: CharSequence? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //告诉FragmentManager:其管理的fragment应接收onCreateOptionsMenu(...)方法的调用指令.
        //fragment中创建菜单
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // view创建之后的操作
        afterCreate(savedInstanceState)
    }

    /**
     * 隐藏状态转变
     */
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        //从隐藏转为非隐藏的时候调用
        //当前页面显示时，显示对应的标题
        if (!hidden) {
            setTitle(mTitle, mBackAble)
        }
    }

    /**
     * 设置标题，设置返回图标
     *
     * @param title    Toolbar标题
     * @param backAble true：Home按钮作为返回箭头，false：默认设置
     */
    protected fun setTitle(title: CharSequence?, backAble: Boolean) {
        if (activity == null) {
            return
        }
        mBackAble = backAble
        mTitle = title
        val supportActionBar = (activity as AppCompatActivity).supportActionBar
        if (activity !is AppCompatActivity || supportActionBar == null) {
            //设置标题
            activity!!.title = mTitle
            return
        }
        //显示标题
        supportActionBar.setDisplayShowTitleEnabled(true)
        //设置标题
        supportActionBar.title = mTitle
        //显示右侧返回图标
        if (backAble) {
            //显示Home按钮
            supportActionBar.setDisplayShowHomeEnabled(true)
            //设置Home按钮作为返回按钮
            supportActionBar.setDisplayHomeAsUpEnabled(true)
            //设置Home按钮图标
            supportActionBar.setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_material)
        }
    }

    /**
     * 设置标题，设置返回图标
     *
     * @param titleId  Toolbar标题
     * @param backAble true：Home按钮作为返回箭头，false：默认设置
     */
    protected fun setTitle(titleId: Int, backAble: Boolean) {
        setTitle(getText(titleId), backAble)
    }
}