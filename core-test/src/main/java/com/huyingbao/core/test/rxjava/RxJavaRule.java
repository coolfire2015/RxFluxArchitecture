package com.huyingbao.core.test.rxjava;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * 切换RxJava异步为同步,立即执行
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public class RxJavaRule implements TestRule {
    private Scheduler mImmediate = new Scheduler() {
        @Override
        public Disposable scheduleDirect(Runnable run, long delay, TimeUnit unit) {
            return super.scheduleDirect(run, 0, unit);
        }

        @Override
        public Worker createWorker() {
            return new ExecutorScheduler.ExecutorWorker(command -> command.run(), true);
        }
    };

    public Scheduler getImmediate() {
        return mImmediate;
    }

    /**
     * 可以设置自定义或者其他调度器,eg:{@link io.reactivex.schedulers.TestScheduler}
     *
     * @param immediate
     */
    public void setImmediate(Scheduler immediate) {
        mImmediate = immediate;
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxJavaPlugins.setInitIoSchedulerHandler(schedulerCallable -> mImmediate);
                RxJavaPlugins.setInitComputationSchedulerHandler(schedulerCallable -> mImmediate);
                RxJavaPlugins.setInitNewThreadSchedulerHandler(schedulerCallable -> mImmediate);
                RxJavaPlugins.setInitSingleSchedulerHandler(schedulerCallable -> mImmediate);
                RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> mImmediate);

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
