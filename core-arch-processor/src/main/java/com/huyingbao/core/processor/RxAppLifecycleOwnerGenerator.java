package com.huyingbao.core.processor;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.Modifier;


/**
 * 生成com.huyingbao.core.arch.RxAppLifecycleOwner类
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
final class RxAppLifecycleOwnerGenerator {
    private static final String PACKAGE_LIFECYCLE = "androidx.lifecycle";
    private static final String PACKAGE_ANNOTATION = "androidx.annotation";
    private static final String PACKAGE_APPLICATION = "android.app";

    private static final String TYPE_NON_NULL = "NonNull";
    private static final String TYPE_LIFECYCLE = "Lifecycle";
    private static final String TYPE_APPLICATION = "Application";
    private static final String TYPE_LIFECYCLE_OWNER = "LifecycleOwner";
    private static final String TYPE_LIFECYCLE_REGISTRY = "LifecycleRegistry";
    private static final String TYPE_GENERATED_RX_APP_LIFECYCLE_OWNER = "RxAppLifecycleOwner";


    private static final String FIELD_LIFECYCLE_REGISTRY = "mRegistry";

    RxAppLifecycleOwnerGenerator() {

    }

    /**
     * 生成类
     */
    TypeSpec generate(Set<String> rxAppLifecycleClassNames) {
        List<String> orderedRxAppLifecycleClassNames = new ArrayList<>(rxAppLifecycleClassNames);
        Collections.sort(orderedRxAppLifecycleClassNames);
        TypeSpec.Builder builder = TypeSpec.classBuilder(TYPE_GENERATED_RX_APP_LIFECYCLE_OWNER)
                //添加修饰符
                .addModifiers(Modifier.FINAL)
                //添加注解
                .addAnnotation(generateAnnotation())
                //添加需要实现的接口
                .addSuperinterface(ClassName.get(PACKAGE_LIFECYCLE, TYPE_LIFECYCLE_OWNER))
                //添加构造方法
                .addMethod(generateConstructor(orderedRxAppLifecycleClassNames))
                //添加成员变量
                .addField(generateField())
                //添加方法
                .addMethod(generateGetLifecycle());
        return builder.build();
    }

    /**
     * 生成类注解
     */
    private AnnotationSpec generateAnnotation() {
        return AnnotationSpec.builder(SuppressWarnings.class)
                .addMember("value", "$S", "deprecation")
                .build();
    }

    /**
     * 生成构造方法
     */
    private MethodSpec generateConstructor(Collection<String> rxAppLifecycleClassNames) {
        //方法入参
        ClassName application = ClassName.get(PACKAGE_APPLICATION, TYPE_APPLICATION);
        ParameterSpec parameterSpec = ParameterSpec.builder(application, "application").build();
        //方法体
        MethodSpec.Builder builder = MethodSpec.constructorBuilder()
                //添加修饰符
                .addModifiers(Modifier.PUBLIC)
                //添加入参
                .addParameter(parameterSpec);
        //添加方法内容
        for (String rxAppLifecycle : rxAppLifecycleClassNames) {
            //获取类名
            ClassName moduleClassName = ClassName.bestGuess(rxAppLifecycle);
            builder.addStatement(FIELD_LIFECYCLE_REGISTRY + ".addObserver(new $T(application))", moduleClassName);
        }
        return builder.build();
    }

    /**
     * 生成成员变量
     */
    private FieldSpec generateField() {
        //获取类名
        ClassName lifecycleRegistry = ClassName.get(PACKAGE_LIFECYCLE, TYPE_LIFECYCLE_REGISTRY);
        FieldSpec.Builder builder = FieldSpec.builder(lifecycleRegistry, FIELD_LIFECYCLE_REGISTRY)
                .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                .initializer("new $T(this)", lifecycleRegistry);
        return builder.build();
    }

    /**
     * 生成getLifecycle()方法
     */
    private MethodSpec generateGetLifecycle() {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("getLifecycle")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(ClassName.get(PACKAGE_ANNOTATION, TYPE_NON_NULL))
                .addAnnotation(Override.class)
                .addStatement("return " + FIELD_LIFECYCLE_REGISTRY)
                .returns(ClassName.get(PACKAGE_LIFECYCLE, TYPE_LIFECYCLE));
        return builder.build();
    }
}
