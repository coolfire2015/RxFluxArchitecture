package ${packageName}.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.huyingbao.core.base.flux.activity.BaseFluxFragActivity
import ${packageName}.store.${storeClass}
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ${activityClass} : BaseFluxFragActivity<${storeClass}>() {
    override val store: ${storeClass}? by viewModels()

    override fun createFragment(): Fragment? {
        return ${fragmentClass}.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
