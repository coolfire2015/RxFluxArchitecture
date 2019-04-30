package com.bumptech.glide.annotation.compiler;

import com.google.auto.service.AutoService;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
public final class GlideAnnotationProcessor extends AbstractProcessor {
    static final boolean DEBUG = false;
    private ProcessorUtil processorUtil;
    private LibraryModuleProcessor libraryModuleProcessor;
    private AppModuleProcessor appModuleProcessor;
    private boolean isGeneratedAppGlideModuleWritten;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        processorUtil = new ProcessorUtil(processingEnvironment);
        IndexerGenerator indexerGenerator = new IndexerGenerator(processorUtil);
        libraryModuleProcessor = new LibraryModuleProcessor(processorUtil, indexerGenerator);
        appModuleProcessor = new AppModuleProcessor(processingEnvironment, processorUtil);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> result = new HashSet<>();
        result.addAll(libraryModuleProcessor.getSupportedAnnotationTypes());
        return result;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     * Each round we do the following:
     * <ol>
     * <li>Find all {@code AppGlideModule}s and save them to an instance variable (throw if > 1).
     * <li>Find all {@code LibraryGlideModule}s
     * <li>For each {@code LibraryGlideModule},
     * write an {@code Indexer} with an Annotation with the class name.
     * <li>If we wrote any {@code Indexer}s, return and wait for the next round.
     * <li>If we didn't write any {@code Indexer}s and there is a {@code AppGlideModule},
     * write the {@code GeneratedAppGlideModule}.
     * Once the {@code GeneratedAppGlideModule} is written, we expect to be finished.
     * Any further generation of related classes will result in errors.
     * </ol>
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment env) {
//    if (set.isEmpty() && !isGeneratedAppGlideModulePending) {
//      return false;
//    }
        processorUtil.process();
        boolean newModulesWritten = libraryModuleProcessor.processModules(env);
        appModuleProcessor.processModules(set, env);

        if (newModulesWritten) {
            if (isGeneratedAppGlideModuleWritten) {
                throw new IllegalStateException("Cannot process annotations after writing AppGlideModule");
            }
            return true;
        }

        if (!isGeneratedAppGlideModuleWritten) {
            isGeneratedAppGlideModuleWritten = appModuleProcessor.maybeWriteAppModule();
        }
        return true;
    }
}
