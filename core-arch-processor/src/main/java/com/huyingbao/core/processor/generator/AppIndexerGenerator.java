package com.huyingbao.core.processor.generator;

import com.huyingbao.core.annotations.AppIndex;
import com.huyingbao.core.processor.ProcessorUtil;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * 生成索引文件
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public final class AppIndexerGenerator {
    private static final String INDEXER_NAME_PREFIX = "AppIndexer_";
    private static final String OBSERVERS = "observers";
    private final ProcessorUtil mProcessorUtil;

    public AppIndexerGenerator(ProcessorUtil processorUtil) {
        this.mProcessorUtil = processorUtil;
    }

    /**
     * 生成类
     *
     * @param types
     * @return
     */
    public TypeSpec generate(List<TypeElement> types) {
        //检索所有@AppObserver的类是否是androidx.lifecycle.LifecycleObserver的实现类
        List<TypeElement> modules = new ArrayList<>();
        for (TypeElement element : types) {
            if (mProcessorUtil.isLifecycleObserver(element)) {
                modules.add(element);
            } else {
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException(element + " is not an androidx.lifecycle.LifecycleObserver interface");
                mProcessorUtil.errorLog(illegalArgumentException.getMessage());
                throw illegalArgumentException;
            }
        }
        //使用libraryModules的第一个元素的标准名拼接成类名
        String name = INDEXER_NAME_PREFIX + types.get(0).getQualifiedName().toString().replace(".", "_");
        return TypeSpec.classBuilder(name)
                .addAnnotation(generateAnnotation(types))
                .addModifiers(Modifier.PUBLIC)
                .build();
    }

    /**
     * 生成注解
     *
     * @param types
     * @return
     */
    private AnnotationSpec generateAnnotation(List<TypeElement> types) {
        AnnotationSpec.Builder annotationBuilder = AnnotationSpec.builder(AppIndex.class);
        for (TypeElement childModule : types) {
            annotationBuilder.addMember(OBSERVERS, "$S", ClassName.get(childModule).toString());
        }
        return annotationBuilder.build();
    }

}
