//package com.huyingbao.core.base
//
//import android.app.Activity
//import android.os.Bundle
//import android.view.MenuItem
//import android.view.View
//import androidx.annotation.IdRes
//import androidx.annotation.LayoutRes
//import androidx.annotation.NavigationRes
//import androidx.appcompat.app.AppCompatActivity
//import androidx.appcompat.widget.Toolbar
//import androidx.fragment.app.FragmentActivity
//import androidx.navigation.NavOptions
//import androidx.navigation.Navigation
//import androidx.navigation.findNavController
//import androidx.navigation.fragment.NavHostFragment
//
//
///**
// * 不使用Dagger.Android，不持有ViewModel，不自动管理订阅
// *
// * 使用[Navigation]管理Fragment
// *
// * Created by liujunfeng on 2019/1/1.
// */
//abstract class BaseNavActivity : AppCompatActivity(), BaseView {
//    /**
//     * 使用默认Activity布局，可以覆盖该方法，使用自定义布局
//     */
//    @LayoutRes
//    override fun getLayoutId() = R.layout.base_activity_nav
//
//    /**
//     * 自定义[Navigation]资源文件
//     */
//    @NavigationRes
//    protected abstract fun getGraphId(): Int
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        //关联布局
//        setContentView(getLayoutId())
//        //使用Toolbar取代原本的actionbar
//        window.decorView.findViewById<Toolbar>(getToolbarId())?.let { setSupportActionBar(it) }
//        //Create之后逻辑
//        afterCreate(savedInstanceState)
//        //设置Navigation
//        setNavigation(getFragmentContainerId(), getGraphId())
//    }
//
//    /**
//     * [androidx.appcompat.widget.Toolbar]Menu点击事件，拦截返回按钮，[androidx.navigation.NavController.navigateUp]弹出 Fragment
//     */
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            // 点击返回图标事件
//            android.R.id.home -> {
//                if (findNavController(getFragmentContainerId()).navigateUp()) {
//                    return true
//                }
//                finish()
//                return true
//            }
//            else -> return super.onOptionsItemSelected(item)
//        }
//    }
//
//    /**
//     * [Navigation]返回实现
//     */
//    override fun onSupportNavigateUp() = findNavController(getFragmentContainerId()).navigateUp()
//}
//
///**
// * 设置导航组件[Navigation]
// */
//fun FragmentActivity.setNavigation(
//    @IdRes contentId: Int,
//    @NavigationRes graphId: Int
//) {
//    //从fragment队列中获取资源ID标识的NavHostFragment，如果不存在，则返回
//    val navHostFragment = (supportFragmentManager.findFragmentById(contentId)
//        ?: return) as? NavHostFragment
//        ?: return
//    //设置导航解析文件
//    navHostFragment.navController.setGraph(graphId)
//}
//
///**
// * Navigation工具方法
// *
// * Created by liujunfeng on 2019/1/1.
// */
//object NavigationUtils {
//    /**
//     * 使用[Navigation]跳转下一个页面，不传参数，不弹出当前页面，不结束当前Activity
//     */
//    fun navigate(view: View, actionId: Int) {
//        navigate(view, actionId, null, popUp = false, finishStack = false)
//    }
//
//    /**
//     * 使用[Navigation]跳转下一个页面，不传参数，不结束当前Activity
//     * @param popUp         true：弹出当前页面
//     */
//    fun navigate(view: View, actionId: Int, popUp: Boolean) {
//        navigate(view, actionId, null, popUp, finishStack = false)
//    }
//
//    /**
//     * 使用[Navigation]跳转下一个页面，不结束当前Activity
//     * @param args          传给Fragment的入参
//     * @param popUp         true：弹出当前页面
//     */
//    fun navigate(view: View, actionId: Int, args: Bundle?, popUp: Boolean) {
//        navigate(view, actionId, args, popUp, finishStack = false)
//    }
//
//    /**
//     * 使用[Navigation]跳转下一个页面
//     *
//     * @param view          持有NavController的View
//     * @param actionId      需要跳转的Action
//     * @param args          传给Fragment的入参
//     * @param popUp         true：弹出当前页面
//     * @param finishStack   true:结束当前activity
//     */
//    fun navigate(view: View, actionId: Int, args: Bundle?, popUp: Boolean, finishStack: Boolean) {
//        val navController = Navigation.findNavController(view)
//        if (popUp) {
//            //弹出然后跳转
//            val navOptions = NavOptions.Builder().setPopUpTo(navController.graph.id, true).build()
//            navController.navigate(actionId, args, navOptions)
//        } else {
//            //跳转
//            navController.navigate(actionId, args)
//        }
//        if (finishStack && view.context is Activity) {
//            //结束当前activity
//            (view.context as Activity).finish()
//        }
//    }
//}
