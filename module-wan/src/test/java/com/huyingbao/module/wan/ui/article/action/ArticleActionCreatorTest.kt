package com.huyingbao.module.wan.ui.article.action

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.huyingbao.core.test.subscriber.BaseSubscriberTest
import com.huyingbao.module.wan.BuildConfig
import com.huyingbao.module.wan.app.WanAppModule
import com.huyingbao.module.wan.ui.article.store.ArticleStore
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

/**
 * 实际调用接口方法，Mock[com.huyingbao.core.arch.store.RxStore]和[com.huyingbao.core.arch.view.SubscriberView]，
 * 验证方法调用之后[com.huyingbao.core.arch.store.RxStore]和[com.huyingbao.core.arch.view.SubscriberView]得到响应。
 *
 * Created by liujunfeng on 2019/3/28.
 */
@RunWith(AndroidJUnit4::class)
@Config(application = HiltTestApplication::class, sdk = [28])
@HiltAndroidTest
@UninstallModules(WanAppModule::class)
@LooperMode(LooperMode.Mode.PAUSED)
class ArticleActionCreatorTest() : BaseSubscriberTest() {
    /**
     * 初始化DaggerMock
     */
    @get:Rule
    override var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named(BuildConfig.MODULE_NAME)
    lateinit var retrofit: Retrofit

    val testDispatcher = TestCoroutineDispatcher()

    @Mock
    lateinit var articleStore: ArticleStore

    private var articleActionCreator: ArticleActionCreator? = null

    override fun getSubscriberList(): List<Any> {
        return listOfNotNull(articleStore)
    }

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        articleActionCreator = ArticleActionCreator(dispatcher, actionManager, retrofit)
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun getArticleList() {
        runBlockingTest {
            //调用接口
            articleActionCreator?.getArticleList(1)
//            Mockito.verify(dispatcher).postAction(any())
            //验证RxStore接收到数据，因为RxStore是mock的，故该方法并不会通知View更新数据
        }
    }


    @Test
    fun getBannerList() {
        //调用接口
        articleActionCreator?.getBannerList()
        //验证RxStore接收到数据，因为RxStore是mock的
        verify(articleStore).onGetBannerLiveData(any())
    }
}