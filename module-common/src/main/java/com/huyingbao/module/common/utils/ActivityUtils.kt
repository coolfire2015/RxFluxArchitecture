package com.huyingbao.module.common.utils

import android.text.TextUtils
import android.widget.FrameLayout
import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment

/**
 * 添加新的Fragment，如果存在旧的Fragment时的操作
 */
enum class FragmentOp {
    /**
     * 直接返回，不添加新的Fragment
     */
    OP_NULL,

    /**
     * 隐藏旧的Fragment，添加新的Fragment，可回退
     */
    OP_HIDE,

    /**
     * 使用新的Fragment替换旧的Fragment
     */
    OP_REPLACE
}

/**
 * 设置需要显示的Fragment，如果存在旧的，看旧的是否是需要显示的Fragment
 *
 * @param contentId     容纳Fragment的View的ID
 * @param fragmentNew   需要显示的Fragment，使用Fragment类名作为tag
 * @param fragmentOp    [FragmentOp]添加新的Fragment，如果存在旧的Fragment时的操作
 */
fun FragmentActivity.setFragment(
        @IdRes contentId: Int?,
        fragmentNew: Fragment?,
        fragmentOp: FragmentOp) {
    //如果ID为空，则返回
    contentId ?: return
    //如果Fragment为空，则返回
    fragmentNew ?: return
    //如果不存在容纳Fragment的FrameLayout，则返回
    findViewById<FrameLayout>(contentId) ?: return
    //如果新的Fragment已经添加，则返回
    if (fragmentNew.isAdded) return
    //功能1:管理fragment队列 2:管理fragment事务回退栈 3:从fragment队列中获取资源ID标识的fragment
    val fragmentOld = supportFragmentManager.findFragmentById(contentId)
    //如果不存在旧的Fragment，直接添加显示新的Fragment
    if (fragmentOld == null) {
        //fragment事务被用来添加,移除,附加,分离或替换fragment队列中的fragment
        //资源ID标识UI fragment是FragmentManager的一种内部实现机制
        //添加fragment供FragmentManager管理时
        //onAttach(Context),onCreate(Bundle)和onCreateView(...)方法会被调用
        //新添加的Fragment的名字，作为Tag使用
        supportFragmentManager.beginTransaction()
                .add(contentId, fragmentNew, fragmentNew.javaClass.simpleName)
                .commit()
        return
    }
    //存在旧的Fragment
    //如果旧的Fragment和新的Fragment同为同一个Fragment的实例对象，则返回
    if (TextUtils.equals(fragmentOld.javaClass.simpleName, fragmentNew.javaClass.simpleName)) return
    //旧的Fragment和新的不同
    when (fragmentOp) {
        //直接返回，不添加新的Fragment
        FragmentOp.OP_NULL -> return
        //隐藏旧的Fragment，添加新的Fragment，可回退
        FragmentOp.OP_HIDE -> supportFragmentManager.beginTransaction()
                .addToBackStack(fragmentOld.javaClass.name)
                .hide(fragmentOld)
                .add(contentId, fragmentNew, fragmentNew.javaClass.simpleName)
                .commit()
        //使用新的Fragment替换旧的Fragment
        FragmentOp.OP_REPLACE -> supportFragmentManager.beginTransaction()
                .replace(contentId, fragmentNew, fragmentNew.javaClass.simpleName)
                .commit()
    }
}

/**
 * 设置导航组件[Navigation]
 */
fun FragmentActivity.setNavigation(@IdRes contentId: Int,
                                   @NavigationRes graphId: Int) {
    //从fragment队列中获取资源ID标识的NavHostFragment，如果不存在，则返回
    val navHostFragment = (supportFragmentManager.findFragmentById(contentId)
            ?: return) as? NavHostFragment
            ?: return
    //设置导航解析文件
    navHostFragment.navController.setGraph(graphId)
}
