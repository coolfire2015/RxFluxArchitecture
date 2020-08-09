package ${packageName}.action

import com.huyingbao.core.arch.action.FlowActionCreator
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.scope.ActivityScope
import javax.inject.Inject

@ActivityScope
class ${actionCreatorClass} @Inject constructor(
        private val rxDispatcher: RxDispatcher,
        private val rxActionManager: RxActionManager
) : FlowActionCreator(rxDispatcher, rxActionManager), ${actionClass} {
}
