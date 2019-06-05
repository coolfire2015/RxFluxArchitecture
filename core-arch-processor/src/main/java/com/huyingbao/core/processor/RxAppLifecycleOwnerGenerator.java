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
final class RxAppLifecycleOwnerGenerator {
    public static final String PACKAGE_GENERATED_ROOT = "com.huyingbao.core.arch";
    private static final String PACKAGE_LIFECYCLE = "androidx.lifecycle";
    private static final String PACKAGE_ANNOTATION = "androidx.annotation";
    private static final String PACKAGE_APPLICATION = "android.app";

    private static final String TYPE_NON_NULL = "NonNull";
    private static final String TYPE_LIFECYCLE = "Lifecycle";
    private static final String TYPE_APPLICATION = "Application";
    private static final String TYPE_LIFECYCLE_OWNER = "LifecycleOwner";
    private static final String TYPE_LIFECYCLE_REGISTRY = "LifecycleRegistry";
    private static final String TYPE_GENERATED_RX_APP_LIFECYCLE_OWNER = "RxAppLifecycleOwner";

    RxAppLifecycleOwnerGenerator() {

    }

    TypeSpec generate(Set<String> rxAppLifecycleClassNames) {
        List<String> orderedRxAppLifecycleClassNames = new ArrayList<>(rxAppLifecycleClassNames);
        Collections.sort(orderedRxAppLifecycleClassNames);
        //添加注解
        AnnotationSpec annotationSpec = AnnotationSpec.builder(SuppressWarnings.class)
                .addMember("value", "$S", "deprecation")
                .build();
        Builder builder = TypeSpec.classBuilder(TYPE_GENERATED_RX_APP_LIFECYCLE_OWNER)
                .addModifiers(Modifier.FINAL)
                //添加注解
                .addAnnotation(annotationSpec)
                //添加需要实现的接口
                .addSuperinterface(ClassName.get(PACKAGE_LIFECYCLE, TYPE_LIFECYCLE_OWNER))
                //添加构造方法
                .addMethod(generateConstructor(orderedRxAppLifecycleClassNames))
                //添加方法
                .addMethod(generateGetLifecycle());
        return builder.build();
    }

    /**
     * 生成getLifecycle()方法
     *
     * @return
     */
    private MethodSpec generateGetLifecycle() {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("getLifecycle")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(ClassName.get(PACKAGE_ANNOTATION, TYPE_NON_NULL))
                .addAnnotation(Override.class)
                .returns(ClassName.get(PACKAGE_LIFECYCLE, TYPE_LIFECYCLE));
        //获取方法名
        ClassName moduleClassName = ClassName.get(PACKAGE_LIFECYCLE, TYPE_LIFECYCLE_REGISTRY);
        builder.addStatement("return new $T(this)", moduleClassName);
        return builder.build();
    }

    /**
     * 生成构造方法
     *
     * @param rxAppLifecycleClassNames
     * @return
     */
    private MethodSpec generateConstructor(Collection<String> rxAppLifecycleClassNames) {
        //方法入参
        ClassName application = ClassName.get(PACKAGE_APPLICATION, TYPE_APPLICATION);
        ParameterSpec parameterSpec = ParameterSpec.builder(application, "application").build();
        MethodSpec.Builder builder = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(parameterSpec);
        for (String rxAppLifecycle : rxAppLifecycleClassNames) {
            //获取方法名
            ClassName moduleClassName = ClassName.bestGuess(rxAppLifecycle);
            builder.addStatement("getLifecycle().addObserver(new $T(application))", moduleClassName);
        }
        return builder.build();
    }
}
