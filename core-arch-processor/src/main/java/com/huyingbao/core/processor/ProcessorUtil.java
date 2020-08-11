package com.huyingbao.core.processor;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;

import static com.huyingbao.core.processor.ArchProcessor.COMPILER_PACKAGE_NAME;
import static com.huyingbao.core.processor.ArchProcessor.DEBUG;


/**
 * Utilities for writing classes and logging.
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public final class ProcessorUtil {
    /**
     * 框架包名
     */
    static final String PACKAGE_ROOT = "com.huyingbao.core.arch";
    /**
     * FluxApp类名
     */
    private static final String TYPE_FLUX_APP = "FluxApp";
    /**
     * FluxApp规范类名
     */
    private static final String QUALIFIED_FLUX_APP = PACKAGE_ROOT + "." + TYPE_FLUX_APP;
    /**
     * LifecycleObserver规范类名
     */
    private static final String QUALIFIED_LIFECYCLE_OBSERVER = "androidx.lifecycle.LifecycleObserver";

    private final ProcessingEnvironment mProcessingEnv;
    /**
     * LifecycleObserver类型元素
     */
    private final TypeElement mLifecycleObserverType;
    /**
     * FluxApp类型元素
     */
    private final TypeElement mFluxAppType;

    private int mRound;

    ProcessorUtil(ProcessingEnvironment processingEnv) {
        this.mProcessingEnv = processingEnv;
        //返回给定其规范名称的类型元素。
        mFluxAppType = processingEnv.getElementUtils().getTypeElement(QUALIFIED_FLUX_APP);
        mLifecycleObserverType = processingEnv.getElementUtils().getTypeElement(QUALIFIED_LIFECYCLE_OBSERVER);
    }

    void process() {
        mRound++;
    }

    /**
     * 判断该类是否是FluxApp的子类
     */
    boolean isFluxApp(TypeElement element) {
        return mProcessingEnv.getTypeUtils().isAssignable(element.asType(),
                mFluxAppType.asType());
    }

    /**
     * 判断该类是否是LifecycleObserver的实现类
     */
    public boolean isLifecycleObserver(TypeElement element) {
        return mProcessingEnv.getTypeUtils().isAssignable(element.asType(), mLifecycleObserverType.asType());
    }

    void writeIndexer(TypeSpec indexer) {
        writeClass(COMPILER_PACKAGE_NAME, indexer);
    }

    void writeClass(String packageName, TypeSpec clazz) {
        try {
            debugLog("Writing class:\n" + clazz);
            JavaFile.builder(packageName, clazz).build().writeTo(mProcessingEnv.getFiler());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    List<TypeElement> getElementsFor(
            Class<? extends Annotation> clazz, RoundEnvironment env) {
        Collection<? extends Element> annotatedElements = env.getElementsAnnotatedWith(clazz);
        return ElementFilter.typesIn(annotatedElements);
    }

    void debugLog(String toLog) {
        if (DEBUG) {
            infoLog(toLog);
        }
    }

    void infoLog(String toLog) {
        mProcessingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "[" + mRound + "] " + toLog);
    }
}
