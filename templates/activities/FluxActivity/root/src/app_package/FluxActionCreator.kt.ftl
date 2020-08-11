package ${packageName}.action

import com.huyingbao.core.arch.action.ActionCreator
import com.huyingbao.core.arch.action.ActionManager
import com.huyingbao.core.arch.dispatcher.Dispatcher
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScope
class ${actionCreatorClass} @Inject constructor(
        private val dispatcher: Dispatcher,
        private val actionManager: ActionManager
) : ActionCreator(dispatcher, actionManager), ${actionClass} {
}
