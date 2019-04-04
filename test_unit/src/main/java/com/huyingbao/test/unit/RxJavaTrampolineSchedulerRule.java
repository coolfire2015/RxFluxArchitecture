package com.huyingbao.test.unit;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by weilu on 2017/12/23.
 */
public class RxJavaTrampolineSchedulerRule implements TestRule {

    private final Scheduler mTrampoline = Schedulers.trampoline();

    public Scheduler getTrampoline() {
        return mTrampoline;
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxJavaPlugins.reset();
                RxJavaPlugins.setIoSchedulerHandler(scheduler -> mTrampoline);
                RxAndroidPlugins.reset();
                RxAndroidPlugins.setMainThreadSchedulerHandler(scheduler -> mTrampoline);
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
