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

import static com.huyingbao.core.processor.RxArchProcessor.DEBUG;


/**
 * Utilities for writing classes and logging.
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
final class ProcessorUtil {
    /**
     * 框架包名
     */
    static final String PACKAGE_ROOT = "com.huyingbao.core.arch";
    /**
     * RxApp类名
     */
    private static final String TYPE_RX_APP = "RxApp";
    /**
     * RxApp规范类名
     */
    private static final String QUALIFIED_RX_APP = PACKAGE_ROOT + "." + TYPE_RX_APP;
    /**
     * App生命周期接口RxAppLifecycle类名
     */
    private static final String TYPE_RX_APP_LIFECYCLE = "RxAppLifecycle";
    /**
     * App生命周期接口类名RxAppLifecycle,规范类名
     */
    private static final String QUALIFIED_RX_APP_LIFECYCLE = PACKAGE_ROOT + "." + TYPE_RX_APP_LIFECYCLE;
    /**
     * 编译文件所在包名
     */
    private static final String PACKAGE_COMPILER = RxArchProcessor.class.getPackage().getName();

    private final ProcessingEnvironment mProcessingEnv;
    /**
     * RxAppLifecycle类型元素
     */
    private final TypeElement mRxAppLifecycleType;
    /**
     * RxApp类型元素
     */
    private final TypeElement mRxAppType;
    private int mRound;

    ProcessorUtil(ProcessingEnvironment processingEnv) {
        this.mProcessingEnv = processingEnv;
        //返回给定其规范名称的类型元素。
        mRxAppType = processingEnv.getElementUtils().getTypeElement(QUALIFIED_RX_APP);
        mRxAppLifecycleType = processingEnv.getElementUtils().getTypeElement(QUALIFIED_RX_APP_LIFECYCLE);
    }

    void process() {
        mRound++;
    }

    /**
     * 判断该类是否是RxApp的子类
     */
    boolean isRxApp(TypeElement element) {
        return mProcessingEnv.getTypeUtils().isAssignable(element.asType(),
                mRxAppType.asType());
    }

    /**
     * 判断该类是否是RxAppLifecycle的实现类
     */
    boolean isRxAppLifecycle(TypeElement element) {
        return mProcessingEnv.getTypeUtils().isAssignable(element.asType(),
                mRxAppLifecycleType.asType());
    }

    void writeIndexer(TypeSpec indexer) {
        writeClass(PACKAGE_COMPILER, indexer);
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
