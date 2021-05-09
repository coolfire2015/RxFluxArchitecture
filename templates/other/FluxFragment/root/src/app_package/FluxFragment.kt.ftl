package ${packageName}

import android.os.Bundle
import com.huyingbao.core.base.flux.fragment.BaseFragment
<#if applicationPackage??>
import ${applicationPackage}.R
</#if>

class ${fragmentClass} : BaseFragment<>() {
    companion object {
        fun newInstance() = ${fragmentClass}()
    }

    override fun getLayoutId() = R.layout.${layoutName}

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
