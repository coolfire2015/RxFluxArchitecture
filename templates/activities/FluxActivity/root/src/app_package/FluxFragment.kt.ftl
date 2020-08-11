package ${packageName}.view

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
<#if applicationPackage??>
import ${applicationPackage}.R
</#if>
import ${packageName}.store.${storeClass}
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ${fragmentClass} : BaseFluxFragment<${storeClass}>() {
    companion object {
        fun newInstance() = ${fragmentClass}()
    }

    override val store: ${storeClass}? by activityViewModels()

    override fun getLayoutId() = R.layout.${layoutName}

    override fun afterCreate(savedInstanceState: Bundle?) {
    }
}
