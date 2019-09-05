package ${packageName}.action

import com.huyingbao.core.arch.action.RxActionCreator
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.scope.ActivityScope
import javax.inject.Inject

@ActivityScope
class ${actionCreatorClass} @Inject constructor(
        rxDispatcher: RxDispatcher,
        rxActionManager: RxActionManager
) : RxActionCreator(rxDispatcher, rxActionManager), ${actionClass} {
}
