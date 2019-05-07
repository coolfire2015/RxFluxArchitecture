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


final class AppModuleGenerator {
    static final String GENERATED_ROOT_MODULE_PACKAGE_NAME = "com.huyingbao.core.arch";
    private static final String GLIDE_LOG_TAG = "RxFlux";
    private static final String GENERATED_APP_MODULE_IMPL_SIMPLE_NAME = "RxAppLifecycleImpl";
    private static final String GENERATED_ROOT_MODULE_SIMPLE_NAME = "RxAppLifecycle";

    private final ProcessorUtil processorUtil;

    AppModuleGenerator(ProcessorUtil processorUtil) {
        this.processorUtil = processorUtil;
    }

    TypeSpec generate(Set<String> libraryGlideModuleClassNames) {
        List<String> orderedLibraryGlideModuleClassNames =
                new ArrayList<>(libraryGlideModuleClassNames);
        Collections.sort(orderedLibraryGlideModuleClassNames);

//        MethodSpec constructor =
//                generateConstructor(
//                        appGlideModuleClassName,
//                        orderedLibraryGlideModuleClassNames);

        MethodSpec registerComponents = generateRegisterComponents(orderedLibraryGlideModuleClassNames);

        Builder builder = TypeSpec.classBuilder(GENERATED_APP_MODULE_IMPL_SIMPLE_NAME)
                .addModifiers(Modifier.FINAL)
                .addAnnotation(
                        AnnotationSpec.builder(SuppressWarnings.class)
                                .addMember("value", "$S", "deprecation")
                                .build()
                )
                .superclass(ClassName.get(GENERATED_ROOT_MODULE_PACKAGE_NAME, GENERATED_ROOT_MODULE_SIMPLE_NAME))
//                .addMethod(constructor)
                .addMethod(registerComponents);
//        for (String glideModule : libraryGlideModuleClassNames) {
//            builder.addField(appGlideModuleClassName, "appGlideModule", Modifier.PRIVATE, Modifier.FINAL)
//        }
        return builder.build();
    }

    private MethodSpec generateRegisterComponents(Collection<String> libraryGlideModuleClassNames) {
        MethodSpec.Builder registerComponents =
                MethodSpec.methodBuilder("registerComponents")
                        .addModifiers(Modifier.PUBLIC)
                        .addAnnotation(Override.class)
                        .addParameter(ParameterSpec.builder(
                                ClassName.get("android.content", "Context"), "context")
                                .addAnnotation(nonNull())
                                .build()
                        )
                        .addParameter(ParameterSpec.builder(
                                ClassName.get("com.bumptech.glide", "Glide"), "glide")
                                .addAnnotation(nonNull())
                                .build()
                        )
                        .addParameter(ParameterSpec.builder(
                                ClassName.get("com.bumptech.glide", "Registry"), "registry")
                                .addAnnotation(nonNull())
                                .build()
                        );

        for (String glideModule : libraryGlideModuleClassNames) {
            ClassName moduleClassName = ClassName.bestGuess(glideModule);
            registerComponents.addStatement(
                    "new $T().registerComponents(context, glide, registry)", moduleClassName);
        }
        // Order matters here. The AppGlideModule must be called last.
        registerComponents.addStatement("appGlideModule.registerComponents(context, glide, registry)");
        return registerComponents.build();
    }

//    /**
//     * 创建构造方法
//     *
//     * @param libraryGlideModuleClassNames
//     * @return
//     */
//    private MethodSpec generateConstructor(Collection<String> libraryGlideModuleClassNames) {
//        MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder();
//        constructorBuilder.addStatement("appGlideModule = new $T()", appGlideModule);
//
//        ClassName androidLogName = ClassName.get("android.util", "Log");
//
//        // Add some log lines to indicate to developers which modules where discovered.
//        constructorBuilder.beginControlFlow("if ($T.isLoggable($S, $T.DEBUG))",
//                androidLogName, GLIDE_LOG_TAG, androidLogName);
//        constructorBuilder.addStatement("$T.d($S, $S)", androidLogName, GLIDE_LOG_TAG,
//                "Discovered AppGlideModule from annotation: " + appGlideModule);
//        // Excluded GlideModule classes from the manifest are logged in Glide's singleton.
//        for (String glideModule : libraryGlideModuleClassNames) {
//            if (excludedGlideModuleClassNames.contains(glideModule)) {
//                constructorBuilder.addStatement("$T.d($S, $S)", androidLogName, GLIDE_LOG_TAG,
//                        "AppGlideModule excludes LibraryGlideModule from annotation: " + glideModule);
//            } else {
//                constructorBuilder.addStatement("$T.d($S, $S)", androidLogName, GLIDE_LOG_TAG,
//                        "Discovered LibraryGlideModule from annotation: " + glideModule);
//            }
//        }
//        constructorBuilder.endControlFlow();
//        return constructorBuilder.build();
//    }
}
