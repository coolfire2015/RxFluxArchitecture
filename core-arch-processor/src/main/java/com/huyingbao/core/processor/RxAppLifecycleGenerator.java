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

import static com.huyingbao.core.processor.ProcessorUtil.nonNull;


final class RxAppLifecycleGenerator {
    static final String GENERATED_ROOT_MODULE_PACKAGE_NAME = "com.huyingbao.core.arch";
    private static final String GENERATED_APP_MODULE_IMPL_SIMPLE_NAME = "RxAppLifecycleImpl";
    private static final String GENERATED_ROOT_MODULE_SIMPLE_NAME = "RxAppLifecycle";

    private final ProcessorUtil processorUtil;

    RxAppLifecycleGenerator(ProcessorUtil processorUtil) {
        this.processorUtil = processorUtil;
    }

    TypeSpec generate(Set<String> libraryGlideModuleClassNames) {
        List<String> orderedLibraryGlideModuleClassNames =
                new ArrayList<>(libraryGlideModuleClassNames);
        Collections.sort(orderedLibraryGlideModuleClassNames);

        MethodSpec onCreate = generateOnCreate(orderedLibraryGlideModuleClassNames);

        Builder builder = TypeSpec.classBuilder(GENERATED_APP_MODULE_IMPL_SIMPLE_NAME)
                .addModifiers(Modifier.FINAL)
                .addAnnotation(
                        AnnotationSpec.builder(SuppressWarnings.class)
                                .addMember("value", "$S", "deprecation")
                                .build()
                )
                .addSuperinterface(ClassName.get(GENERATED_ROOT_MODULE_PACKAGE_NAME, GENERATED_ROOT_MODULE_SIMPLE_NAME))
                .addMethod(onCreate);
        return builder.build();
    }

    private MethodSpec generateOnCreate(Collection<String> libraryGlideModuleClassNames) {
        MethodSpec.Builder builder =
                MethodSpec.methodBuilder("onCreate")
                        .addModifiers(Modifier.PUBLIC)
                        .addAnnotation(Override.class)
                        .addParameter(ParameterSpec.builder(
                                ClassName.get("android.app", "Application"), "application")
                                .addAnnotation(nonNull())
                                .build()
                        );

        for (String glideModule : libraryGlideModuleClassNames) {
            ClassName moduleClassName = ClassName.bestGuess(glideModule);
            builder.addStatement(
                    "new $T().onCreate(application)", moduleClassName);
        }
        return builder.build();
    }
}
