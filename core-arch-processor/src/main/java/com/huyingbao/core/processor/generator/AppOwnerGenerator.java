package com.huyingbao.core.processor.generator;

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
 * 自动生成com.huyingbao.core.arch.FinalAppLifecycleOwner
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public final class AppOwnerGenerator {
    private static final String PACKAGE_LIFECYCLE = "androidx.lifecycle";
    private static final String PACKAGE_ANNOTATION = "androidx.annotation";
    private static final String PACKAGE_APPLICATION = "android.app";

    private static final String TYPE_NON_NULL = "NonNull";
    private static final String TYPE_LIFECYCLE = "Lifecycle";
    private static final String TYPE_APPLICATION = "Application";
    private static final String TYPE_LIFECYCLE_OWNER = "LifecycleOwner";
    private static final String TYPE_LIFECYCLE_REGISTRY = "LifecycleRegistry";
    private static final String TYPE_GENERATED_APP_LIFECYCLE_OWNER = "FinalAppLifecycleOwner";
    private static final String TYPE_GENERATED_APP_LIFECYCLE_OWNER_ENTRY_POINT = "AppLifecycleOwnerEntryPoint";

    private static final String PACKAGE_HILT = "dagger.hilt";
    private static final String TYPE_HILT_ENTRY_POINT = "EntryPoint";
    private static final String TYPE_HILT_ENTRY_POINTS = "EntryPoints";
    private static final String TYPE_HILT_INSTALL_IN = "InstallIn";

    private static final String PACKAGE_HILT_APPLICATION_COMPONENT = PACKAGE_HILT + ".components";
    private static final String TYPE_HILT_APPLICATION_COMPONENT = "SingletonComponent";

    private static final String FIELD_LIFECYCLE_REGISTRY = "mRegistry";

    public AppOwnerGenerator() {

    }

    /**
     * 生成类
     */
    public TypeSpec generate(Set<String> appLifecycleObserverSet) {
        List<String> orderedFluxAppLifecycleClassNames = new ArrayList<>(appLifecycleObserverSet);
        Collections.sort(orderedFluxAppLifecycleClassNames);
        TypeSpec.Builder builder = TypeSpec.classBuilder(TYPE_GENERATED_APP_LIFECYCLE_OWNER)
                //添加修饰符
                .addModifiers(Modifier.FINAL)
                //添加注解
                .addAnnotation(generateAnnotation())
                //添加需要实现的接口
                .addSuperinterface(ClassName.get(PACKAGE_LIFECYCLE, TYPE_LIFECYCLE_OWNER))
                //添加构造方法
                .addMethod(generateConstructor(orderedFluxAppLifecycleClassNames))
                //添加成员变量
                .addField(generateField())
                //添加方法
                .addMethod(generateGetLifecycle())
                //添加接口
                .addType(generateEntryPoint(orderedFluxAppLifecycleClassNames));
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
    private MethodSpec generateConstructor(Collection<String> appLifecycleObserverSet) {
        //方法入参
        ClassName application = ClassName.get(PACKAGE_APPLICATION, TYPE_APPLICATION);
        ClassName entryPoints = ClassName.get(PACKAGE_HILT, TYPE_HILT_ENTRY_POINTS);
        ClassName appLifecycleOwnerEntryPoint = ClassName.get("", TYPE_GENERATED_APP_LIFECYCLE_OWNER_ENTRY_POINT);
        ParameterSpec parameterSpec = ParameterSpec.builder(application, "application").build();
        //方法体
        MethodSpec.Builder builder = MethodSpec.constructorBuilder()
                //添加修饰符
                .addModifiers(Modifier.PUBLIC)
                //添加入参
                .addParameter(parameterSpec);
        //添加方法内容
        for (String appLifecycleObserver : appLifecycleObserverSet) {
            //获取类名
            ClassName moduleClassName = ClassName.bestGuess(appLifecycleObserver);
            builder.addStatement(FIELD_LIFECYCLE_REGISTRY + ".addObserver($T.get(application, $T.class).get$T())",
                    entryPoints,
                    appLifecycleOwnerEntryPoint,
                    moduleClassName);
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

    /**
     * 生成EntryPoint接口
     */
    private TypeSpec generateEntryPoint(Collection<String> appLifecycleObserverSet) {
        TypeSpec.Builder typeSpecBuilder = TypeSpec.interfaceBuilder(TYPE_GENERATED_APP_LIFECYCLE_OWNER_ENTRY_POINT)
                //添加注解@EntryPoint
                .addAnnotation(ClassName.get(PACKAGE_HILT, TYPE_HILT_ENTRY_POINT))
                //添加注解@InstallIn(SingletonComponent.class)
                .addAnnotation(AnnotationSpec.builder(ClassName.get(PACKAGE_HILT, TYPE_HILT_INSTALL_IN))
                        .addMember("value", "$T.class", ClassName.get(PACKAGE_HILT_APPLICATION_COMPONENT, TYPE_HILT_APPLICATION_COMPONENT))
                        .build());
        //添加方法内容
        for (String appLifecycleObserver : appLifecycleObserverSet) {
            //获取类名
            ClassName moduleClassName = ClassName.bestGuess(appLifecycleObserver);
            //方法体
            MethodSpec methodSpec = MethodSpec.methodBuilder("get" + moduleClassName.simpleName())
                    .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                    .returns(moduleClassName)
                    .build();
            typeSpecBuilder.addMethod(methodSpec);
        }
        return typeSpecBuilder.build();
    }
}
