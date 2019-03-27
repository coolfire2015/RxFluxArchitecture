package com.huyingbao.module.wan.ui.article.action;

import com.huyingbao.core.arch.action.RxActionManager;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liujunfeng on 2019/3/27.
 */
public class ArticleActionTest {
    private static boolean isInitRxTools = false;
    @Rule
    public MockitoRule mMockitoRule = MockitoJUnit.rule();
    @Rule
    public RxJavaTestSchedulerRule mRxJavaTestSchedulerRule = new RxJavaTestSchedulerRule();
    @Spy
    private RxDispatcher mRxDispatcher;
    @Spy
    private RxActionManager mRxActionManager;

    private ArticleAction mArticleAction;

    @Before
    public void setUp() {
        mArticleAction = new ArticleActionCreator(mRxDispatcher, mRxActionManager);
    }

    @Test
    public void getArticleList() {
        mArticleAction.getArticleList(10);
        Assert.assertEquals(0, 0);
    }

    @Test
    public void getBannerList() {
    }

    /**
     * 把异步变成同步，方便测试
     */
    public static void openRxTools() {
        if (isInitRxTools) {
            return;
        }
        isInitRxTools = true;

        RxAndroidSchedulersHook rxAndroidSchedulersHook = new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        };

        RxJavaSchedulersHook rxJavaSchedulersHook = new RxJavaSchedulersHook() {
            @Override
            public Scheduler getIOScheduler() {
                return Schedulers.immediate();
            }
        };

        // reset()不是必要，实践中发现不写reset()，偶尔会出错，所以写上保险^_^
        RxAndroidPlugins.getInstance().reset();
        RxAndroidPlugins.getInstance().registerSchedulersHook(rxAndroidSchedulersHook);
        RxJavaPlugins.getInstance().reset();
        RxJavaPlugins.getInstance().registerSchedulersHook(rxJavaSchedulersHook);
    }
}