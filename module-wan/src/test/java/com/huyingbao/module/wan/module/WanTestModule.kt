package com.huyingbao.module.wan.module

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.gson.GsonBuilder
import com.huyingbao.module.common.app.CommonAppModule
import com.huyingbao.module.wan.BuildConfig
import com.huyingbao.module.wan.app.WanAppDatabase
import com.huyingbao.module.wan.app.WanAppModule
import com.huyingbao.module.wan.ui.article.store.ArticleStore
import dagger.Component
import dagger.Module
import dagger.Provides
import io.appflate.restmock.JVMFileParser
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.RESTMockServerStarter
import io.appflate.restmock.utils.RequestMatchers
import it.cosenonjaviste.daggermock.DaggerMock
import okhttp3.OkHttpClient
import retrofit2.Retrofit

import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * 全局静态方法,提供依赖注入器实例对象。
 *
 * Created by liujunfeng on 2019/7/1.
 */
object WanMockUtils {
    var wanTestComponent: WanTestComponent? = null
}

/**
 * 依赖注入器,为测试代码提供方便测试的全局对象。
 */
@Singleton
@Component(modules = [WanTestModule::class])
interface WanTestComponent {
    /**
     * 提供实际创建的工具对象
     */
    val retrofit: Retrofit
}

/**
 * 依赖注入仓库
 *
 * 1.提供[org.mockito.Spy]和[org.mockito.Mock]对象
 *
 * 2.提供测试代码需要的全局对象
 */
@Module(includes = [WanAppModule::class])
class WanTestModule {
    /**
     * 返回实际创建的[ArticleStore]实例对象，但是DaggerMock会返回虚拟的Mock对象，参见[wanMockDaggerRule]方法。
     * 如果[WanTestComponent]不定义该对象，该方法不会被调用。
     *
     * @param storeFactory 来自[CommonAppModule]，[ArticleStore]对象来自[WanAppModule]
     */
    @Singleton
    @Provides
    fun provideArticleStore(storeFactory: ViewModelProvider.Factory): ArticleStore {
        return storeFactory.create(ArticleStore::class.java)
    }

    /**
     * 初始化Retrofit，覆盖[WanAppModule.provideRetrofit]方法
     *
     * @param builder 来自[CommonAppModule]
     */
    @Singleton
    @Provides
    fun provideRetrofit(builder: OkHttpClient.Builder): Retrofit {
        val retrofitBuilder = Retrofit.Builder()
                .baseUrl(if (BuildConfig.MOCK_URL) initMockServer() else WanAppModule.BASE_API)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build())
        return retrofitBuilder.build()
    }

    /**
     * 提供[RoomDatabase]单例对象，获得创建内存数据库的实例，覆盖[WanAppModule.provideDataBase]方法
     */
    @Singleton
    @Provides
    fun provideDataBase(application: Application): WanAppDatabase {
        val databaseBuilder = Room.inMemoryDatabaseBuilder(
                application,
                WanAppDatabase::class.java)
                //允许Room破坏性地重新创建数据库表。
                .fallbackToDestructiveMigration()
        return databaseBuilder.build()
    }
}

/**
 * 动态创建 Module 子类的 JUnit 规则，初始化一个测试类里面的所有用@Mock field为mock对象。
 *
 * 完成的操作：要使用哪个Module、要build哪个Component、要把build好的Component放到哪
 *
 * 1.动态创建了一个[WanTestModule]的子类，返回在测试中定义并使用[org.mockito.Mock]和[org.mockito.Spy]标注的虚拟对象 ，而不是真实的对象。
 *
 * 2.Mock [WanTestModule]，通过反射的方式得到[WanTestModule]的所有[dagger.Provides]方法，如果有某个方法的返回值是[org.mockito.Mock]和[org.mockito.Spy]标注的虚拟对象，
 * 那么就使用Mockito，让这个[dagger.Provides]方法被调用时，返回虚拟对象。
 *
 * 3.使用[WanTestModule]来构建一个[WanTestComponent]，并且放到[WanMockUtils]里面去。
 */
fun wanMockDaggerRule() = DaggerMock.rule<WanTestComponent>(WanTestModule()) {
    set {
        WanMockUtils.wanTestComponent = it
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
