package com.huyingbao.core.processor;

import com.huyingbao.core.annotations.RxAppObserver;
import com.huyingbao.core.annotations.RxIndex;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

public class RxArchProcessor extends AbstractProcessor {
    private static final String COMPILER_PACKAGE_NAME = RxArchProcessor.class.getPackage().getName();
    static final boolean DEBUG = false;
    private boolean mIsGeneratedWritten;
    private ProcessorUtil mProcessorUtil;
    private RxIndexerGenerator mRxIndexerGenerator;
    private RxAppLifecycleOwnerGenerator mRxAppLifecycleGenerator;

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
        mProcessorUtil = new ProcessorUtil(processingEnvironment);
        mRxIndexerGenerator = new RxIndexerGenerator(mProcessorUtil);
        mRxAppLifecycleGenerator = new RxAppLifecycleOwnerGenerator();
    }

    /**
     * @return 返回要处理的注解的集合;
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> result = new HashSet<>();
        result.add(RxAppObserver.class.getCanonicalName());
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
        //编译RxAppObserver注解生成索引RxIndexer_文件
        boolean newIndexWritten = processIndex(roundEnvironment);
        if (newIndexWritten) {
            return true;
        }
        if (!mIsGeneratedWritten) {
            //生成全局RxAppLifecycleImpl文件
            mIsGeneratedWritten = processRxLifecycleImpl();
        }
        return true;
    }

    /**
     * 生成代理索引文件
     *
     * @param roundEnvironment
     * @return
     */
    private boolean processIndex(RoundEnvironment roundEnvironment) {
        List<TypeElement> elements = mProcessorUtil.getElementsFor(RxAppObserver.class, roundEnvironment);
        mProcessorUtil.debugLog("Processing types : " + elements);
        if (elements.isEmpty()) {
            return false;
        }
        TypeSpec spec = mRxIndexerGenerator.generate(elements);
        mProcessorUtil.writeIndexer(spec);
        return true;
    }

    /**
     * 生成全局AppLifecycle实现类
     *
     * @return
     */
    private boolean processRxLifecycleImpl() {
        PackageElement packageElement = processingEnv.getElementUtils().getPackageElement(COMPILER_PACKAGE_NAME);
        //所有module中的编译生成的索引文件
        Set<String> indexedClassNames = getIndexedClassNames(packageElement);
        //生成全局RxLifecycleImpl文件
        TypeSpec generatedRxLifecycleImpl = mRxAppLifecycleGenerator.generate(indexedClassNames);
        mProcessorUtil.writeClass(
                RxAppLifecycleOwnerGenerator.PACKAGE_GENERATED_ROOT,
                generatedRxLifecycleImpl);
        return true;
    }

    /**
     * 从当前包附加的类中获取到所有编译生成的Index类
     * 再从Index类的RxIndex注解中取出modules中存储的自定义RxAppLifecycle
     *
     * @param packageElement
     * @return
     */
    @SuppressWarnings("unchecked")
    private Set<String> getIndexedClassNames(PackageElement packageElement) {
        Set<String> rxAppLifecycles = new HashSet<>();
        //获取当前包元素附加的所有元素
        List<? extends Element> rxAppLifecycleGeneratedElements = packageElement.getEnclosedElements();
        for (Element indexer : rxAppLifecycleGeneratedElements) {
            RxIndex annotation = indexer.getAnnotation(RxIndex.class);
            // If the annotation is null, it means we've come across another class in the same package
            // that we can safely ignore.
            if (annotation != null) {
                Collections.addAll(rxAppLifecycles, annotation.modules());
            }
        }
        mProcessorUtil.debugLog("Found RxAppLifecycle: " + rxAppLifecycles);
        return rxAppLifecycles;
    }
}
