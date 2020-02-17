package ${packageName}.module

import com.huyingbao.core.arch.scope.FragmentScope
import ${packageName}.view.${fragmentClass}
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ${activityModuleClass} {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun inject${fragmentClass}(): ${fragmentClass}
}
