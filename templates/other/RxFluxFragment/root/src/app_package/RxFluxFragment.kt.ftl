package ${packageName}.view

import android.os.Bundle
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
<#if applicationPackage??>
import ${applicationPackage}.R
</#if>

class ${fragmentClass} : BaseFluxFragment<>() {
    companion object {
        fun newInstance() = ${fragmentClass}()
    }

    override fun getLayoutId() = R.layout.${layoutName}

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
