package com.huyingbao.core.processor;

import com.huyingbao.core.annotations.AppDelegate;
import com.squareup.javapoet.TypeSpec;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

public class AppDelegateProcessor extends AbstractProcessor {
    static final boolean DEBUG = false;
    /**
     * 日志相关的辅助类
     */
    private Messager mMessage;
    private ProcessorUtil mProcessorUtil;
    private IndexerGenerator mIndexerGenerator;

    /**
     * 可以初始化拿到一些使用的工具
     * 比如文件相关的辅助类 Filer;
     * 元素相关的辅助类Elements;
     * 日志相关的辅助类Messager;
     *
     * @param processingEnvironment
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mMessage = processingEnvironment.getMessager();
        mProcessorUtil = new ProcessorUtil(processingEnvironment);
        mIndexerGenerator = new IndexerGenerator(mProcessorUtil);
    }

    /**
     * @return 返回要处理的注解的集合;
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> result = new HashSet<>();
        result.add(AppDelegate.class.getCanonicalName());
        return result;
    }

    /**
     * @return 返回 Java 版本;
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return super.getSupportedSourceVersion();
    }

    /**
     * 生成Java文件
     *
     * @param set
     * @param roundEnvironment
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        mProcessorUtil.process();
        List<TypeElement> elements = mProcessorUtil.getElementsFor(AppDelegate.class, roundEnvironment);
        mProcessorUtil.debugLog("Processing types : " + elements);
        if (elements.isEmpty()) {
            return false;
        }
        TypeSpec spec = mIndexerGenerator.generate(elements);
        mProcessorUtil.writeIndexer(spec);
        return true;
    }
}
