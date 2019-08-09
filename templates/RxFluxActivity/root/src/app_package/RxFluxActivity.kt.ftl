package ${packageName}.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.huyingbao.core.base.flux.activity.BaseFluxFragActivity
import ${packageName}.store.${storeClass}

class ${activityClass} : BaseFluxFragActivity<${storeClass}>() {
    override fun createFragment(): Fragment? {
        return ${fragmentClass}.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
