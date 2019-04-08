package com.huyingbao.test.unit;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.TestScheduler;

/**
 * RxJava异步切换同步调度器,提供时间控制调度器
 * Created by weilu on 2018/1/6..
 */
public class RxJavaTimeTestSchedulerRule implements TestRule {

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
                RxJavaPlugins.setComputationSchedulerHandler(scheduler -> mTestScheduler);
                RxJavaPlugins.setNewThreadSchedulerHandler(scheduler -> mTestScheduler);
                RxJavaPlugins.setSingleSchedulerHandler(scheduler -> mTestScheduler);
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
