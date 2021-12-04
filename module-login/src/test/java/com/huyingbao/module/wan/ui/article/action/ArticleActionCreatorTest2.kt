package com.huyingbao.module.wan.ui.article.action

import com.google.gson.GsonBuilder
import com.huyingbao.core.arch.action.ActionManager
import com.huyingbao.core.arch.dispatcher.Dispatcher
import com.huyingbao.core.arch.model.Action
import com.huyingbao.core.arch.model.Loading
import com.huyingbao.module.common.app.CommonAppConstants
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import io.appflate.restmock.JVMFileParser
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.RESTMockServerStarter
import io.appflate.restmock.utils.RequestMatchers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.greenrobot.eventbus.EventBus
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Spy
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import java.util.logging.Level

/**
 * 实际调用接口方法，Mock[com.huyingbao.core.arch.store.RxStore]和[com.huyingbao.core.arch.view.SubscriberView]，
 * 验证方法调用之后[com.huyingbao.core.arch.store.RxStore]和[com.huyingbao.core.arch.view.SubscriberView]得到响应。
 *
 * Created by liujunfeng on 2019/3/28.
 */
class ArticleActionCreatorTest2 {
    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Spy
    lateinit var dispatcher: Dispatcher

    @Spy
    lateinit var actionManager: ActionManager

    private var articleActionCreator: ArticleActionCreator? = null

    @ExperimentalCoroutinesApi
    @Test
    fun getArticleList() {
        val testCoroutineDispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(testCoroutineDispatcher)
        val action = Action.Builder("Action").build()
        runBlockingTest {
            flow {
//                emit(retrofit().create(ArticleApi::class.java).getArticleList(1))
                emit(1)
            }
                .flowOn(Dispatchers.IO)
                // 调用开始
                .onStart {
                    dispatcher.postLoading(Loading.newInstance(action, false))
                }
//                // 操作进行中
                .onEach {
                    dispatcher.postAction(action)
                    action.setResponse(it)
                }
                // 调用结束
                .onCompletion {
                    dispatcher.postLoading(Loading.newInstance(action, true))
                }
                // 捕获异常
                .catch {
                    // 操作异常，打印错误日志
                    EventBus.getDefault().logger.log(
                        Level.SEVERE,
                        "ActionCreator onError:${it.message}"
                    )
                }
                // 指定主线程执行collect操作
                .launchIn(MainScope())
            Mockito.verify(dispatcher, times(1)).postAction(any())
            Mockito.verify(dispatcher, times(2)).postLoading(any())
        }
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()
    }
}


/**
 * 使用RESTMockServer,为需要测试的接口提供mock数据
 */
fun initMockServer(): String {
    //开启RestMockServer
    RESTMockServerStarter.startSync(JVMFileParser())
    //article/list
    RESTMockServer.whenGET(RequestMatchers.pathContains("article/list"))
        .thenReturnFile(200, "json/articleList.json")
    //banner/json
    RESTMockServer.whenGET(RequestMatchers.pathContains("banner/json"))
        .thenReturnFile(200, "json/bannerList.json")
    //login
    RESTMockServer.whenPOST(RequestMatchers.pathContains("user/login"))
        .thenReturnFile(200, "json/login.json")
    //register
    RESTMockServer.whenPOST(RequestMatchers.pathContains("user/register"))
        .thenReturnFile(200, "json/register.json")
    //返回Mock的Url
    return RESTMockServer.getUrl()
}

fun retrofit(): Retrofit {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(CommonAppConstants.Config.HTTP_TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(CommonAppConstants.Config.HTTP_TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(CommonAppConstants.Config.HTTP_TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .build()
    val retrofitBuilder = Retrofit.Builder()
        .baseUrl("www.baidu.com")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
        .client(okHttpClient)
    return retrofitBuilder.build()
}

