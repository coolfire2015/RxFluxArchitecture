package com.huyingbao.core.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.lang.annotation.Annotation;
import java.util.Arrays;
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
 */
final class ProcessorUtil {
    /**
     * 框架包名
     */
    private static final String PACKAGE_NAME = "com.huyingbao.core.arch";
    /**
     * RxApp类名
     */
    private static final String RX_APP_SIMPLE_NAME = "RxApp";
    /**
     * RxApp规范类名
     */
    private static final String RX_APP_QUALIFIED_NAME =
            PACKAGE_NAME + "." + RX_APP_SIMPLE_NAME;
    /**
     * App生命周期接口RxAppLifecycle类名
     */
    private static final String RX_APP_LIFECYCLE_SIMPLE_NAME = "RxAppLifecycle";
    /**
     * App生命周期接口类名RxAppLifecycle,规范类名
     */
    private static final String RX_APP_LIFECYCLE_QUALIFIED_NAME =
            PACKAGE_NAME + "." + RX_APP_LIFECYCLE_SIMPLE_NAME;
    /**
     * 编译文件所在包名
     */
    private static final String COMPILER_PACKAGE_NAME =
            RxArchProcessor.class.getPackage().getName();
    /**
     * 非空注解
     */
    private static final ClassName NONNULL_ANNOTATION =
            ClassName.get("android.support.annotation", "NonNull");
    /**
     * 非空注解
     */
    private static final ClassName JETBRAINS_NOTNULL_ANNOTATION =
            ClassName.get("org.jetbrains.annotations", "NotNull");
    /**
     * 非空注解
     */
    private static final ClassName ANDROIDX_NONNULL_ANNOTATION =
            ClassName.get("androidx.annotation", "NonNull");
    private static final ClassName CHECK_RESULT_ANNOTATION =
            ClassName.get("android.support.annotation", "CheckResult");
    private static final ClassName ANDROIDX_CHECK_RESULT_ANNOTATION =
            ClassName.get("androidx.annotation", "CheckResult");

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
        mRxAppType = processingEnv.getElementUtils().getTypeElement(RX_APP_QUALIFIED_NAME);
        mRxAppLifecycleType = processingEnv.getElementUtils().getTypeElement(RX_APP_LIFECYCLE_QUALIFIED_NAME);
    }

    void process() {
        mRound++;
    }

    /**
     * 判断该类是否是RxApp的子类
     *
     * @param element
     * @return
     */
    boolean isRxApp(TypeElement element) {
        return mProcessingEnv.getTypeUtils().isAssignable(element.asType(),
                mRxAppType.asType());
    }

    /**
     * 判断该类是否是RxAppLifecycle的实现类
     *
     * @param element
     * @return
     */
    boolean isRxAppLifecycle(TypeElement element) {
        return mProcessingEnv.getTypeUtils().isAssignable(element.asType(),
                mRxAppLifecycleType.asType());
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

    static ClassName nonNull() {
        try {
            Class.forName(ANDROIDX_NONNULL_ANNOTATION.reflectionName());
            return ANDROIDX_NONNULL_ANNOTATION;
        } catch (ClassNotFoundException e) {
            return NONNULL_ANNOTATION;
        }
    }

    static ClassName checkResult() {
        try {
            Class.forName(ANDROIDX_CHECK_RESULT_ANNOTATION.reflectionName());
            return ANDROIDX_CHECK_RESULT_ANNOTATION;
        } catch (ClassNotFoundException e) {
            return CHECK_RESULT_ANNOTATION;
        }
    }

    static List<ClassName> nonNulls() {
        return Arrays.asList(NONNULL_ANNOTATION, JETBRAINS_NOTNULL_ANNOTATION,
                ANDROIDX_NONNULL_ANNOTATION);
    }
}
