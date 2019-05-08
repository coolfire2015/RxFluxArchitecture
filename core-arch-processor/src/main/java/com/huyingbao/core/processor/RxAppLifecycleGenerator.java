package com.huyingbao.core.processor;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.Modifier;


/**
 * 生成com.huyingbao.core.arch.RxAppLifecycleImpl类
 */
final class RxAppLifecycleGenerator {
    static final String GENERATED_ROOT_PACKAGE_NAME = "com.huyingbao.core.arch";
    private static final String GENERATED_ROOT_SIMPLE_NAME = "RxAppLifecycle";
    private static final String GENERATED_RX_APP_LIFECYCLE_IMPL_SIMPLE_NAME = "RxAppLifecycleImpl";

    private final ProcessorUtil mProcessorUtil;

    RxAppLifecycleGenerator(ProcessorUtil processorUtil) {
        this.mProcessorUtil = processorUtil;
    }

    TypeSpec generate(Set<String> rxAppLifecycleClassNames) {
        List<String> orderedRxAppLifecycleClassNames = new ArrayList<>(rxAppLifecycleClassNames);
        Collections.sort(orderedRxAppLifecycleClassNames);
        //生成方法
        MethodSpec onCreate = generateOnCreate(orderedRxAppLifecycleClassNames);
        //添加注解
        AnnotationSpec annotationSpec = AnnotationSpec.builder(SuppressWarnings.class)
                .addMember("value", "$S", "deprecation")
                .build();
        Builder builder = TypeSpec.classBuilder(GENERATED_RX_APP_LIFECYCLE_IMPL_SIMPLE_NAME)
                .addModifiers(Modifier.FINAL)
                //添加注解
                .addAnnotation(annotationSpec)
                //添加需要实现的接口
                .addSuperinterface(ClassName.get(GENERATED_ROOT_PACKAGE_NAME, GENERATED_ROOT_SIMPLE_NAME))
                //添加方法
                .addMethod(onCreate);
        return builder.build();
    }

    /**
     * 生成onCreate(Application application)方法
     *
     * @param rxAppLifecycleClassNames
     * @return
     */
    private MethodSpec generateOnCreate(Collection<String> rxAppLifecycleClassNames) {
        //方法入参
        ClassName application = ClassName.get("android.app", "Application");
        ParameterSpec parameterSpec = ParameterSpec.builder(application, "application").build();
        MethodSpec.Builder builder = MethodSpec.methodBuilder("onCreate")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(parameterSpec);
        for (String rxAppLifecycle : rxAppLifecycleClassNames) {
            ClassName moduleClassName = ClassName.bestGuess(rxAppLifecycle);
            builder.addStatement("new $T().onCreate(application)", moduleClassName);
        }
        return builder.build();
    }
}
