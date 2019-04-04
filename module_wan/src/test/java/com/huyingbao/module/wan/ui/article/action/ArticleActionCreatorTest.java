package com.huyingbao.module.wan.ui.article.action;

import com.huyingbao.core.arch.action.RxActionManager;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.module.wan.action.WanApi;
import com.huyingbao.module.wan.module.MockComponent;
import com.huyingbao.module.wan.module.MockModule;
import com.huyingbao.test.unit.RxJavaImmediateSchedulerRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.appflate.restmock.JVMFileParser;
import io.appflate.restmock.RESTMockServer;
import io.appflate.restmock.RESTMockServerStarter;
import io.appflate.restmock.utils.RequestMatchers;
import it.cosenonjaviste.daggermock.DaggerMockRule;

import static org.mockito.Mockito.times;

/**
 * Created by liujunfeng on 2019/3/27.
 */
public class ArticleActionCreatorTest {
    private WanApi mWanApi;
    @Mock
    private RxDispatcher mRxDispatcher;
    @Mock
    private RxActionManager mRxActionManager;

    @Rule
    public MockitoRule mMockitoRule = MockitoJUnit.rule();
    @Rule
    public RxJavaImmediateSchedulerRule mRule = new RxJavaImmediateSchedulerRule();
    @Rule
    public DaggerMockRule<MockComponent> rule = new DaggerMockRule<>(MockComponent.class, new MockModule())
            .set(new DaggerMockRule.ComponentSetter<MockComponent>() {
                @Override
                public void setComponent(MockComponent component) {
                    //启动RESTMock服务
                    RESTMockServerStarter.startSync(new JVMFileParser());
                    mWanApi = component.getWanApi();
                }
            });

    private ArticleActionCreator mArticleAction;

    @Before
    public void setUp() {
        mArticleAction = new ArticleActionCreator(mRxDispatcher, mRxActionManager, mWanApi);
    }

    @Test
    public void getArticleList() {
        RESTMockServer.whenGET(RequestMatchers.pathContains("article/list"))
                .thenReturnFile(200, "json/articleList.json");
        mArticleAction.getArticleList(1);
        //调用方法成功,发送一次RxAction
        Mockito.verify(mRxDispatcher).postRxAction(Mockito.any());
        //调用方法成功,发送两次RxLoading
        Mockito.verify(mRxDispatcher, times(2)).postRxLoading(Mockito.any());
    }

    @Test
    public void getBannerList() {
        mArticleAction.getBannerList();
        Mockito.verify(mRxDispatcher).postRxAction(Mockito.any());
    }
}