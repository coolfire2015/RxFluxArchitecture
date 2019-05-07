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

import static com.huyingbao.core.processor.AppDelegateProcessor.DEBUG;


/**
 * Utilities for writing classes and logging.
 */
final class ProcessorUtil {
    private static final String MODULE_PACKAGE_NAME = "com.huyingbao.core.arch";
    private static final String LIBRARY_MODULE_SIMPLE_NAME = "RxAppLifecycle";
    private static final String LIBRARY_MODULE_QUALIFIED_NAME =
            MODULE_PACKAGE_NAME + "." + LIBRARY_MODULE_SIMPLE_NAME;
    private static final String COMPILER_PACKAGE_NAME = AppDelegateProcessor.class.getPackage().getName();

    private final ProcessingEnvironment processingEnv;
    private final TypeElement mRxAppLifecycleModuleType;
    private int round;

    ProcessorUtil(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
        //返回给定其规范名称的类型元素。
        mRxAppLifecycleModuleType = processingEnv.getElementUtils().getTypeElement(LIBRARY_MODULE_QUALIFIED_NAME);
    }

    void process() {
        round++;
    }

    boolean isRxAppLifecycle(TypeElement element) {
        return processingEnv.getTypeUtils().isAssignable(element.asType(),
                mRxAppLifecycleModuleType.asType());
    }

    void writeIndexer(TypeSpec indexer) {
        writeClass(COMPILER_PACKAGE_NAME, indexer);
    }

    void writeClass(String packageName, TypeSpec clazz) {
        try {
            debugLog("Writing class:\n" + clazz);
            JavaFile.builder(packageName, clazz).build().writeTo(processingEnv.getFiler());
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
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "[" + round + "] " + toLog);
    }
}
