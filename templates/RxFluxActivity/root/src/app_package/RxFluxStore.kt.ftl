package ${packageName}.store

import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.store.RxActivityStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ${storeClass} @Inject constructor(rxDispatcher: RxDispatcher) : RxActivityStore(rxDispatcher) {
}
