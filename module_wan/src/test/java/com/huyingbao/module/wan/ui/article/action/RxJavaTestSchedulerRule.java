package com.huyingbao.module.wan.ui.article.action;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.TestScheduler;

/**
 * Created by liujunfeng on 2019/3/27.
 */
public class RxJavaTestSchedulerRule implements TestRule {

    private final TestScheduler mTestScheduler = new TestScheduler();

    public TestScheduler getTestScheduler() {
        return mTestScheduler;
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxJavaPlugins.setIoSchedulerHandler(scheduler -> mTestScheduler);
                RxJavaPlugins.setNewThreadSchedulerHandler(scheduler -> mTestScheduler);
                RxJavaPlugins.setComputationSchedulerHandler(scheduler -> mTestScheduler);
                RxAndroidPlugins.setMainThreadSchedulerHandler(scheduler -> mTestScheduler);
                try {
                    base.evaluate();
                } finally {
                    RxJavaPlugins.reset();
                    RxAndroidPlugins.reset();
                }
            }
        };
    }
}
