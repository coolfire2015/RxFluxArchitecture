package com.huyingbao.core.processor;

import com.huyingbao.core.annotations.RxAppDelegate;
import com.huyingbao.core.annotations.RxIndex;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeSpec;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

final class RxIndexerGenerator {
    private static final String INDEXER_NAME_PREFIX = "AppDelegateIndexer_";
    private final ProcessorUtil processorUtil;

    RxIndexerGenerator(ProcessorUtil processorUtil) {
        this.processorUtil = processorUtil;
    }

    TypeSpec generate(List<TypeElement> types) {
        List<TypeElement> modules = new ArrayList<>();
        for (TypeElement element : types) {
            if (processorUtil.isRxAppLifecycle(element)) {
                modules.add(element);
            } else {
                throw new IllegalArgumentException("Unrecognized type: " + element);
            }
        }
        return generate(types, RxAppDelegate.class);
    }

    private static TypeSpec generate(List<TypeElement> libraryModules,
                                     Class<? extends Annotation> annotation) {
        AnnotationSpec.Builder annotationBuilder =
                AnnotationSpec.builder(RxIndex.class);

        String value = getAnnotationValue(annotation);
        for (TypeElement childModule : libraryModules) {
            annotationBuilder.addMember(value, "$S", ClassName.get(childModule).toString());
        }

        StringBuilder indexerName = new StringBuilder(INDEXER_NAME_PREFIX);
        for (TypeElement element : libraryModules) {
            indexerName.append(element.getQualifiedName().toString().replace(".", "_"));
            indexerName.append("_");
        }
        indexerName = new StringBuilder(indexerName.substring(0, indexerName.length() - 1));

        return TypeSpec.classBuilder(indexerName.toString())
                .addAnnotation(annotationBuilder.build())
                .addModifiers(Modifier.PUBLIC)
                .build();
    }

    private static String getAnnotationValue(Class<? extends Annotation> annotation) {
        if (annotation == RxAppDelegate.class) {
            return "modules";
        } else {
            throw new IllegalArgumentException("Unrecognized annotation: " + annotation);
        }
    }
}
