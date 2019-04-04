package com.huyingbao.test.unit;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * 切换RxJava异步为同步
 *
 * @author liujunfeng
 * @date 2019/1/1
 */
public class RxJavaTestSchedulerRuleStatic implements TestRule {

    /**
     * 立即执行的调度器
     */
    private Scheduler mImmediate = new Scheduler() {

        @Override
        public Disposable scheduleDirect(@NonNull Runnable run, long delay, @NonNull TimeUnit unit) {
            return super.scheduleDirect(run, 0, unit);
        }

        @Override
        public Worker createWorker() {
            return new ExecutorScheduler.ExecutorWorker(command -> command.run(), true);
        }
    };

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
