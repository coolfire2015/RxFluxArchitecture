package com.huyingbao.module.common.app

//import com.huyingbao.module.common.ui.update.view.CommonUpdateDialog
import android.app.Application
import android.content.Context
import com.huyingbao.core.annotations.AppObserver
import com.huyingbao.core.arch.FluxLifecycleCallback
import com.huyingbao.core.arch.dispatcher.Dispatcher
import com.huyingbao.core.arch.model.Action
import com.huyingbao.core.arch.model.Error
import com.huyingbao.core.arch.model.Loading
import com.huyingbao.core.arch.store.AppStore
import com.huyingbao.module.common.ui.update.action.AppAction
import com.huyingbao.module.common.ui.update.model.AppBean
import com.huyingbao.module.common.ui.update.model.AppUpdateState
import com.huyingbao.module.common.ui.update.model.getAppState
import com.huyingbao.module.common.utils.showCommonError
import com.huyingbao.module.common.utils.showCommonLoading
import dagger.hilt.android.qualifiers.ApplicationContext
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.toast
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 全局Store，可以接受全局的[Error]、[Loading]。
 *
 * 通过[FluxLifecycleCallback]持有的Activity栈，来进行对应的操作。
 *
 * Created by liujunfeng on 2019/8/1.
 */
@Singleton
@AppObserver
class CommonAppStore @Inject constructor(
        @ApplicationContext private val context: Context,
        private val fluxLifecycleCallback: FluxLifecycleCallback,
        private val dispatcher: Dispatcher
) : AppStore(dispatcher) {
    /**
     * 接收[Error]，粘性
     */
    @Subscribe(sticky = true)
    fun onError(error: Error) {
        fluxLifecycleCallback.activityStack.peek()?.let { showCommonError(it, error) }
    }

    /**
     * 显示或隐藏进度对话框，接收[Loading]，粘性
     */
    @Subscribe(sticky = true)
    fun onLoading(rxLoading: Loading) {
        fluxLifecycleCallback.activityStack.peek()?.let { showCommonLoading(it, rxLoading) }
    }

    /**
     * 显示更新弹框
     */
    @Subscribe(tags = [AppAction.GET_APP_LATEST])
    fun onGetAppLatest(rxAction: Action) {
        rxAction.getResponse<AppBean>()?.let {
            it.appUpdateState = getAppState(
                    build = it.build,
                    packageName = context.packageName,
                    application = context as Application)
            val activity = fluxLifecycleCallback.activityStack.peek() ?: return
            if (it.appUpdateState == AppUpdateState.LATEST) {
                activity.toast("当前已是最新版本")
                return
            }
//            if (activity is AppCompatActivity) {
//                val fragmentByTag = activity.supportFragmentManager
//                        .findFragmentByTag(CommonUpdateDialog::class.java.simpleName)
//                if (fragmentByTag == null) {
//                    CommonUpdateDialog
//                            .newInstance(
//                                    apkUrl = it.install_url,
//                                    changelog = it.changelog,
//                                    appUpdateState = it.appUpdateState)
//                            .show(
//                                    activity.supportFragmentManager,
//                                    CommonUpdateDialog::class.java.simpleName)
//                }
//            }
        }
    }
}

/**
 * Created by liujunfeng on 2019/1/1.
 */
interface CommonAppAction {
    companion object {
        /**
         * 滑动到顶部
         */
        const val SCROLL_TO_TOP = "scrollToTop"

        /**
         * 需要获取下一页数据
         */
        const val GET_NEXT_PAGE = "getNextPage"
    }
}
