package com.huyingbao.core.common.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import butterknife.ButterKnife
import butterknife.Unbinder
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.arch.model.RxError
import com.huyingbao.core.arch.view.RxFluxView
import com.huyingbao.core.common.R
import dagger.android.support.AndroidSupportInjection
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

/**
 * Created by liujunfeng on 2019/1/1.
 */
abstract class CommonRxFragment<T : ViewModel> : Fragment(), CommonView, RxFluxView<T> {
    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory

    private var mStore: T? = null
    private var mUnbinder: Unbinder? = null

    private var mBackAble: Boolean = false
    private var mTitle: CharSequence? = null

    private var mTvTop: TextView? = null
    private var mToolbarTop: Toolbar? = null
    private var mActionBarTop: ActionBar? = null

    protected var mIsVisibleToUser: Boolean = false

    override val rxStore: T?
        get() {
            if (mStore == null) {
                val tClass = (javaClass.getGenericSuperclass() as ParameterizedType).actualTypeArguments[0] as Class<T>
                mStore = ViewModelProviders.of(activity!!, mViewModelFactory).get(tClass)
            }
            return mStore
        }

    override fun onAttach(context: Context) {
        //依赖注入
        AndroidSupportInjection.inject(this)
        //告诉FragmentManager:其管理的fragment应接收onCreateOptionsMenu(...)方法的调用指令.
        //fragment中创建菜单
        setHasOptionsMenu(true)
        //实例化宿主Activity中的ActionBar
        initActionBar()
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //ButterKnife绑定
        mUnbinder = ButterKnife.bind(this, view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // view创建之后的操作
        afterCreate(savedInstanceState)
    }

    /**
     * 接收RxChange，粘性
     */
    @Subscribe(sticky = true)
    override fun onRxChanged(rxChange: RxChange) {
        //收到后，移除粘性通知
        EventBus.getDefault().removeStickyEvent(rxChange)
    }

    /**
     * 接收RxError，粘性
     * 该方法不经过store,
     * 由fragment直接处理
     */
    @Subscribe(sticky = true)
    override fun onRxError(error: RxError) {
        //收到后，移除粘性通知
        EventBus.getDefault().removeStickyEvent(error)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mUnbinder!!.unbind()
    }

    /**
     * 可见状态转变
     *
     * @param isVisibleToUser
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.mIsVisibleToUser = isVisibleToUser
    }

    /**
     * 隐藏状态转变
     *
     * @param hidden
     */
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        //从隐藏转为非隐藏的时候调用
        if (!hidden) {//当前页面显示时，显示对应的标题
            setTitle(mTitle, mBackAble)
        }
    }

    private fun initActionBar() {
        val view = activity!!.window.decorView
        mToolbarTop = view.findViewById(R.id.tlb_top)
        mTvTop = view.findViewById(R.id.tv_top_title)
        if (mToolbarTop == null || activity !is AppCompatActivity) return
        //取代原本的actionbar
        (activity as AppCompatActivity).setSupportActionBar(mToolbarTop)
        //设置actionbar
        mActionBarTop = (activity as AppCompatActivity).supportActionBar
        if (mActionBarTop == null) return
        //不显示home图标
        mActionBarTop!!.setDisplayShowHomeEnabled(false)
        //不显示标题
        mActionBarTop!!.setDisplayShowTitleEnabled(false)
    }

    protected fun setTitle(title: CharSequence?, backAble: Boolean) {
        mBackAble = backAble
        mTitle = title
        if (mTvTop == null) return
        //设置标题
        mTvTop!!.text = mTitle
        if (mActionBarTop == null) return
        //显示右侧返回图标
        mActionBarTop!!.setDisplayHomeAsUpEnabled(backAble)
        if (backAble) mActionBarTop!!.setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_material)
    }

    protected fun setTitle(titleId: Int, backAble: Boolean) {
        setTitle(getText(titleId), backAble)
    }
}
