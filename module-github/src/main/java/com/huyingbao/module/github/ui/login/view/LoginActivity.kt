package com.huyingbao.module.github.ui.login.view

import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.base.activity.BaseRxActivity
import com.huyingbao.core.common.module.CommonContants
import com.huyingbao.core.utils.ActivityUtils
import com.huyingbao.module.github.R
import com.huyingbao.module.github.app.GithubAppStore
import com.huyingbao.module.github.ui.login.action.LoginAction
import com.huyingbao.module.github.ui.login.store.LoginStore
import com.huyingbao.module.github.ui.main.view.MainActivity
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject

/**
 * 登录页面
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Route(path = CommonContants.Address.LoginActivity)
class LoginActivity : BaseRxActivity<LoginStore>() {
    @Inject
    lateinit var githubAppStore: GithubAppStore
    @JvmField
    @Autowired(name = CommonContants.Key.IS_TO_LOGIN)
    var isToLogin: Boolean = false

    override fun getLayoutId(): Int {
        return R.layout.base_activity_frag
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        if (isToLogin) {
            //登录认证失败，ARouter导航过来
            ActivityUtils.setFragment(this, R.id.fl_container, LoginFragment.newInstance(), false)
        } else {
            //正常页面跳转
            ActivityUtils.setFragment(this, R.id.fl_container, StartFragment.newInstance(), false)
        }
    }

    /**
     * 获取当前登录用户信息成功，跳转主页面
     */
    @Subscribe(sticky = true, tags = [LoginAction.GET_LOGIN_USER_INFO])
    fun toMainActivity(rxChange: RxChange) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    /**
     * 跳转登录页面
     */
    @Subscribe(sticky = true, tags = [LoginAction.TO_LOGIN_FRAGMENT])
    fun toLoginFragment(rxChange: RxChange) {
        ActivityUtils.setFragment(this, R.id.fl_container, LoginFragment.newInstance(), false)
    }
}
