package ${packageName}.action

import com.huyingbao.core.arch.action.FlowActionCreator
import com.huyingbao.core.arch.action.FlowActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScope
class ${actionCreatorClass} @Inject constructor(
        private val rxDispatcher: RxDispatcher,
        private val rxActionManager: FlowActionManager
) : FlowActionCreator(rxDispatcher, rxActionManager), ${actionClass} {
}
