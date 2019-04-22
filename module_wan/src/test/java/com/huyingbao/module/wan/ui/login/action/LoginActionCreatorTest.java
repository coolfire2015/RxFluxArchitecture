package com.huyingbao.module.wan.ui.login.action;

import com.huyingbao.core.arch.action.RxActionManager;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.util.LocalStorageUtils;
import com.huyingbao.module.wan.module.MockDaggerRule;
import com.huyingbao.module.wan.module.MockUtils;
import com.huyingbao.module.wan.ui.login.store.LoginStore;
import com.huyingbao.test.utils.RxJavaRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.verify;

/**
 * Created by liujunfeng on 2019/4/3.
 */
public class LoginActionCreatorTest {
    @Rule
    public RxJavaRule mRxJavaRule = new RxJavaRule();
    @Rule
    public MockitoRule mMockitoRule = MockitoJUnit.rule();
    @Rule
    public MockDaggerRule mMockDaggerRule = new MockDaggerRule();

    @Spy
    private RxDispatcher mRxDispatcher;
    @Spy
    private RxActionManager mRxActionManager;
    @Mock
    private LoginStore mLoginStore;
    @Mock
    private LocalStorageUtils mLocalStorageUtils;

    private LoginActionCreator mLoginActionCreator;

    @Before
    public void setUp() throws Exception {
        mLoginActionCreator = new LoginActionCreator(mRxDispatcher, mRxActionManager, MockUtils.getComponent().getWanApi());
        mLoginActionCreator.mLocalStorageUtils = mLocalStorageUtils;
        mRxDispatcher.subscribeRxStore(mLoginStore);
    }

    @After
    public void tearDown() {
        mRxDispatcher.unsubscribeRxStore(mLoginStore);
    }

    @Test
    public void testRegister() {
        mLoginActionCreator.register("coolfire", "123456", "123456");
        //验证方法正确执行,并发送RxAction
        verify(mRxDispatcher).postRxAction(Mockito.any());
        //验证RxStore的正确方法接受对应的RxAction
        verify(mLoginStore).onRegister(Mockito.any());
    }

    @Test
    public void testLogin() {
        mLoginActionCreator.login("coolfire", "123456");
        //验证方法正确执行,并发送RxAction
        verify(mRxDispatcher).postRxAction(Mockito.any());
        //验证RxStore的正确方法接受对应的RxAction
        verify(mLoginStore).onLogin(Mockito.any());
    }

    @Test
    public void testChangeBaseUrl() {
        mLoginActionCreator.changeBaseUrl("asdf");
        verify(mLocalStorageUtils).setString(Mockito.any(), Mockito.any());
        //验证方法正确执行,并发送RxAction
        verify(mRxDispatcher).postRxAction(Mockito.any());
        //验证发送之后,正确移除RxAction
        verify(mRxActionManager).remove(Mockito.any());
    }

    /**
     * 全部测试运行时,忽略该测试
     * 需要单独测试
     */
    @Test
    @Ignore
    public void testGetIdentify() {
        //设置新的调度器
        mRxJavaRule.setImmediate(new TestScheduler());
        //调用待测试方法
        mLoginActionCreator.getIdentify();
        //调用调度器方法,时间到5秒
        ((TestScheduler) mRxJavaRule.getImmediate()).advanceTimeTo(5, TimeUnit.SECONDS);
        //验证倒计时完成方法 未被调用
        verify(mRxActionManager, Mockito.never()).remove(Mockito.any());
        //验证方法被调用5次
        verify(mRxDispatcher, Mockito.times(5)).postRxAction(Mockito.any());
        //调用调度器方法,时间到10秒
        ((TestScheduler) mRxJavaRule.getImmediate()).advanceTimeTo(10, TimeUnit.SECONDS);
        //验证方法被调用10次
        verify(mRxDispatcher, Mockito.times(10)).postRxAction(Mockito.any());
        //验证倒计时完成方法 被调用
        verify(mRxActionManager).remove(Mockito.any());
    }
}