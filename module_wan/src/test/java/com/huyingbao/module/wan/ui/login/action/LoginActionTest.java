package com.huyingbao.module.wan.ui.login.action;

import com.huyingbao.core.arch.action.RxActionManager;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.test.RxJavaTestSchedulerRule;
import com.huyingbao.module.wan.action.WanApi;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

/**
 * Created by liujunfeng on 2019/4/3.
 */
public class LoginActionTest {
    @Rule
    public RxJavaTestSchedulerRule mRxJavaTestSchedulerRule = new RxJavaTestSchedulerRule();
    @Rule
    public MockitoRule mMockitoRule = MockitoJUnit.rule();

    @Mock
    private RxActionManager mRxActionManager;
    @Mock
    private RxDispatcher mRxDispatcher;
    private LoginActionCreator mLoginAction;
    private WanApi mWanApi;

    @Before
    public void setUp() {
        mLoginAction = new LoginActionCreator(mRxDispatcher, mRxActionManager, mWanApi);
    }

    @Test
    public void register() {
    }

    @Test
    public void login() {
    }
}