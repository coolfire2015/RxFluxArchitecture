package com.huyingbao.core.processor;

import com.huyingbao.core.annotations.AppDelegate;
import com.huyingbao.core.annotations.Index;
import com.squareup.javapoet.TypeSpec;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

public class AppDelegateProcessor extends AbstractProcessor {
    private static final String COMPILER_PACKAGE_NAME =
            AppDelegateProcessor.class.getPackage().getName();
    static final boolean DEBUG = false;
    /**
     * 日志相关的辅助类
     */
    private Messager mMessage;
    private ProcessorUtil mProcessorUtil;
    private IndexerGenerator mIndexerGenerator;
    private boolean isGeneratedAppGlideModuleWritten;
    private AppModuleGenerator appModuleGenerator;

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
        appModuleGenerator = new AppModuleGenerator(mProcessorUtil);
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
        boolean newIndexWritten = processIndex(roundEnvironment);
        if (newIndexWritten) {
            return true;
        }
        if (!isGeneratedAppGlideModuleWritten) {
//            isGeneratedAppGlideModuleWritten = maybeWriteAppModule();
        }
        return true;
    }

    /**
     * 生成代理索引
     *
     * @param roundEnvironment
     * @return
     */
    private boolean processIndex(RoundEnvironment roundEnvironment) {
        List<TypeElement> elements = mProcessorUtil.getElementsFor(AppDelegate.class, roundEnvironment);
        mProcessorUtil.debugLog("Processing types : " + elements);
        if (elements.isEmpty()) {
            return false;
        }
        TypeSpec spec = mIndexerGenerator.generate(elements);
        mProcessorUtil.writeIndexer(spec);
        return true;
    }

    /**
     * 生成全局代理
     *
     * @return
     */
    private boolean maybeWriteAppModule() {
        // If this package is null, it means there are no classes with this package name. One way this
        // could happen is if we process an annotation and reach this point without writing something
        // to the package. We do not error check here because that shouldn't happen with the
        // current implementation.
        PackageElement glideGenPackage =
                processingEnv.getElementUtils().getPackageElement(COMPILER_PACKAGE_NAME);
        TypeSpec generatedAppGlideModule =
                appModuleGenerator.generate(getIndexedClassNames(glideGenPackage));
        mProcessorUtil.writeClass(AppModuleGenerator.GENERATED_ROOT_MODULE_PACKAGE_NAME, generatedAppGlideModule);
        return false;
    }

    /**
     * 从当前包附加的类中获取到所有编译生成的Index类
     *
     * @param glideGenPackage
     * @return
     */
    @SuppressWarnings("unchecked")
    private Set<String> getIndexedClassNames(PackageElement glideGenPackage) {
        Set<String> glideModules = new HashSet<>();
        List<? extends Element> glideGeneratedElements = glideGenPackage.getEnclosedElements();
        for (Element indexer : glideGeneratedElements) {
            Index annotation = indexer.getAnnotation(Index.class);
            // If the annotation is null, it means we've come across another class in the same package
            // that we can safely ignore.
            if (annotation != null) {
                Collections.addAll(glideModules, annotation.modules());
            }
        }
        mProcessorUtil.debugLog("Found GlideModules: " + glideModules);
        return glideModules;
    }

    void processModules(Set<? extends TypeElement> set, RoundEnvironment env) {
        for (TypeElement element : processorUtil.getElementsFor(GlideModule.class, env)) {
            if (processorUtil.isAppGlideModule(element)) {
                appGlideModules.add(element);
            }
        }

        processorUtil.debugLog("got app modules: " + appGlideModules);

        if (appGlideModules.size() > 1) {
            throw new IllegalStateException(
                    "You cannot have more than one AppGlideModule, found: " + appGlideModules);
        }
    }
}
