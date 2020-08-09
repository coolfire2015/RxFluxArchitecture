package com.huyingbao.core.processor;

import com.huyingbao.core.annotations.AppIndex;
import com.huyingbao.core.annotations.AppLifecycleObserver;
import com.huyingbao.core.annotations.AppLifecycleOwner;
import com.huyingbao.core.processor.generator.AppIndexerGenerator;
import com.huyingbao.core.processor.generator.AppOwnerGenerator;
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

/**
 * Created by liujunfeng on 2019/1/1.
 */
public class ArchProcessor extends AbstractProcessor {
    /**
     * 编译文件所在包名
     */
    public static final String COMPILER_PACKAGE_NAME = ArchProcessor.class.getPackage().getName();
    /**
     * 是否debug
     */
    public static final boolean DEBUG = true;
    /**
     * 工具类
     */
    private ProcessorUtil mProcessorUtil;
    /**
     * 索引生成类
     */
    private AppIndexerGenerator mRxIndexerGenerator;
    /**
     * com.huyingbao.core.arch.FinalAppLifecycleOwner生成类
     */
    private AppOwnerGenerator mAppOwnerGenerator;
    /**
     * 列表中只能有一个使用{@link AppLifecycleOwner}注解的RxApp子类
     */
    private boolean mIsGeneratedWritten;
    /**
     * 存放{@link AppLifecycleOwner}注解的RxApp的list
     */
    private final List<TypeElement> mRxAppList = new ArrayList<>();

    /**
     * 可以初始化拿到一些使用的工具
     * 比如文件相关的辅助类 Filer;
     * 元素相关的辅助类Elements;
     * 日志相关的辅助类Messager;
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mProcessorUtil = new ProcessorUtil(processingEnvironment);
        mRxIndexerGenerator = new AppIndexerGenerator(mProcessorUtil);
        mAppOwnerGenerator = new AppOwnerGenerator();
    }

    /**
     * 只需要处理{@link AppLifecycleOwner}和{@link AppLifecycleObserver}
     *
     * @return 返回要处理的注解的集合;
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> result = new HashSet<>();
        result.add(AppLifecycleOwner.class.getCanonicalName());
        result.add(AppLifecycleObserver.class.getCanonicalName());
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
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        mProcessorUtil.process();
        //编译RxAppObserver注解生成索引RxIndexer_文件
        boolean newIndexWritten = processIndex(roundEnvironment);
        //编译RxAppOwner注解
        processRxAppOwner(roundEnvironment);
        if (newIndexWritten) {
            return true;
        }
        if (!mIsGeneratedWritten) {
            //生成全局AppLifecycleOwner文件
            mIsGeneratedWritten = processAppLifecycleOwner();
        }
        return true;
    }

    /**
     * 检索{@link AppLifecycleObserver}标注的com.huyingbao.core.arch.FinalAppLifecycleOwner实现类。
     * <p>
     * 生成索引文件。
     *
     * @param roundEnvironment
     * @return
     */
    private boolean processIndex(RoundEnvironment roundEnvironment) {
        List<TypeElement> elements = mProcessorUtil.getElementsFor(AppLifecycleObserver.class, roundEnvironment);
        mProcessorUtil.debugLog("Processing types : " + elements);
        if (elements.isEmpty()) {
            return false;
        }
        TypeSpec typeSpec = mRxIndexerGenerator.generate(elements);
        mProcessorUtil.writeIndexer(typeSpec);
        return true;
    }

    /**
     * 检查使用{@link AppLifecycleOwner}注解的类中是否有RxApp子类，如果有则取出，且RxApp子类只能有一个。
     *
     * @param roundEnvironment
     */
    private void processRxAppOwner(RoundEnvironment roundEnvironment) {
        List<TypeElement> elements = mProcessorUtil.getElementsFor(AppLifecycleOwner.class, roundEnvironment);
        for (TypeElement element : elements) {
            if (mProcessorUtil.isRxApp(element)) {
                mRxAppList.add(element);
            }
        }
        mProcessorUtil.debugLog("got app modules: " + mRxAppList);
        if (mRxAppList.size() > 1) {
            throw new IllegalStateException("You cannot have more than one RxApp, found: " + mRxAppList);
        }
    }

    /**
     * 生成com.huyingbao.core.arch.FinalAppLifecycleOwner
     *
     * @return
     */
    private boolean processAppLifecycleOwner() {
        //如果没有使用{@link RxAppOwner}注解的RxApp子类，直接返回。
        if (mRxAppList.isEmpty()) {
            return false;
        }
        //获取编译自动生成的索引文件的包名，每一个module都有同样的包名。
        PackageElement packageElement = processingEnv.getElementUtils().getPackageElement(COMPILER_PACKAGE_NAME);
        //所有module中的编译生成的RxAppLifecycle索引文件
        Set<String> indexedClassNames = getAppLifecycleObserverSet(packageElement);
        //如果没有RxAppLifecycle索引文件，则返回
        if (indexedClassNames == null || indexedClassNames.size() == 0) {
            return false;
        }
        //生成全局com.huyingbao.core.arch.FinalAppLifecycleOwner
        TypeSpec finalAppLifecycleOwner = mAppOwnerGenerator.generate(indexedClassNames);
        mProcessorUtil.writeClass(ProcessorUtil.PACKAGE_ROOT, finalAppLifecycleOwner);
        return true;
    }

    /**
     * 从当前包附加的类中获取到所有编译生成的Index类，
     * <p>
     * 再从Index类的{@link AppIndex}注解中取出observers中存储的androidx.lifecycle.LifecycleObserver实现类名
     *
     * @param packageElement 当前的包路径：com.huyingbao.core.processor
     * @return
     */
    @SuppressWarnings("unchecked")
    private Set<String> getAppLifecycleObserverSet(PackageElement packageElement) {
        //如果不存在当前的包路径返回空
        if (packageElement == null) {
            return null;
        }
        Set<String> appLifecycleObserverSet = new HashSet<>();
        //获取当前包元素附加的所有元素（AppIndex类）
        List<? extends Element> appIndexElements = packageElement.getEnclosedElements();
        for (Element indexer : appIndexElements) {
            AppIndex annotation = indexer.getAnnotation(AppIndex.class);
            // If the annotation is null, it means we've come across another class in the same package that we can safely ignore.
            if (annotation != null) {
                Collections.addAll(appLifecycleObserverSet, annotation.observers());
            }
        }
        mProcessorUtil.debugLog("Found appLifecycleObserverSet: " + appLifecycleObserverSet);
        return appLifecycleObserverSet;
    }
}
