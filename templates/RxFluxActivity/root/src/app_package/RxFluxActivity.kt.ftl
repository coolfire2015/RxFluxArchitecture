package ${packageName}.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.huyingbao.core.base.activity.BaseRxFragActivity
import ${packageName}.store.${storeClass}

class ${activityClass} : BaseRxFragActivity<${storeClass}>() {
    override fun createFragment(): Fragment? {
        return ${fragmentClass}.newInstance()
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
