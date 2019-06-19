package com.huyingbao.core.base.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import com.huyingbao.core.arch.model.RxError
import com.huyingbao.core.arch.model.RxLoading
import com.huyingbao.core.arch.model.RxRetry
import com.huyingbao.core.arch.store.RxActivityStore
import com.huyingbao.core.arch.view.RxFluxActivity
import com.huyingbao.core.base.BaseView
import com.huyingbao.core.common.R
import com.huyingbao.core.common.action.CommonActionCreator
import com.huyingbao.core.common.dialog.CommonLoadingDialog
import com.huyingbao.core.common.model.CommonException
import org.greenrobot.eventbus.Subscribe
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject


/**
 * 带有[Toolbar]的Activity父类
 *
 * Created by liujunfeng on 2019/1/1.
 */
abstract class BaseRxActivity<T : RxActivityStore> : RxFluxActivity<T>(), BaseView {
    init {
        //Vector使用
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    @Inject
    lateinit var mCommonActionCreator: CommonActionCreator
    @Inject
    lateinit var mLoadingDialogLazy: CommonLoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initActionBar()
        afterCreate(savedInstanceState)
    }

    /**
     * 默认使用[Toolbar]取代ActionBar
     */
    private fun initActionBar() {
        val view = window.decorView
        val toolbar = view.findViewById<Toolbar>(R.id.tlb_top)
        if (toolbar != null) {
            //使用Toolbar取代原本的actionbar
            setSupportActionBar(toolbar)
        }
    }

    /**
     * [Toolbar]菜单点击事件，拦截返回按钮
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // 点击返回图标事件
            android.R.id.home -> {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    //如果Fragment回退栈不为空，先弹出Fragment
                    supportFragmentManager.popBackStack()
                    return true
                }
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    /**
     * 接收[RxError]，粘性
     * 该方法不经过RxStore,
     * 由RxFluxView直接处理
     */
    @Subscribe(sticky = true)
    fun onRxError(rxError: RxError) {
        when (val throwable = rxError.throwable) {
            is CommonException -> Toast.makeText(this, throwable.message(), Toast.LENGTH_SHORT).show()
            is retrofit2.HttpException -> Toast.makeText(this, throwable.code().toString() + ":服务器问题", Toast.LENGTH_SHORT).show()
            is SocketException -> Toast.makeText(this, "网络异常!", Toast.LENGTH_SHORT).show()
            is UnknownHostException -> Toast.makeText(this, "网络异常!", Toast.LENGTH_SHORT).show()
            is SocketTimeoutException -> Toast.makeText(this, "连接超时!", Toast.LENGTH_SHORT).show()
            else -> Toast.makeText(this, throwable.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 接收[RxRetry]，粘性
     * 该方法不经过RxStore,
     * 由RxFluxView直接处理
     */
    @Subscribe(sticky = true)
    fun onRxRetry(rxRetry: RxRetry<*>) {
        val coordinatorLayout = findViewById<CoordinatorLayout>(R.id.cdl_content) ?: return
        Snackbar.make(coordinatorLayout, rxRetry.tag, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry") { mCommonActionCreator.postRetryAction(rxRetry) }
                .show()
    }

    /**
     * 显示进度对话框
     * 接收[RxLoading]，粘性
     * 该方法不经过RxStore,
     * 由RxFluxView直接处理
     */
    @Subscribe(sticky = true)
    fun onRxLoading(rxLoading: RxLoading) {
        val fragmentByTag = supportFragmentManager.findFragmentByTag(rxLoading.tag)
        if (fragmentByTag == null && rxLoading.isLoading) {
            //显示进度框
            mLoadingDialogLazy.show(supportFragmentManager, rxLoading.tag)
            return
        }
        if (fragmentByTag is CommonLoadingDialog && !rxLoading.isLoading) {
            //隐藏进度框
            fragmentByTag.dismiss()
        }
    }
}