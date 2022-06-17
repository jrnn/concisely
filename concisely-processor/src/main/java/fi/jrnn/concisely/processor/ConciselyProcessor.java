/*
 * Copyright (c) 2022 Juho Juurinen
 *
 * Licensed under the MIT license: https://opensource.org/licenses/MIT
 */
package fi.jrnn.concisely.processor;

import static java.lang.String.format;
import static java.util.stream.Collectors.toSet;
import static javax.tools.Diagnostic.Kind.NOTE;

import fi.jrnn.concisely.VisibleForTesting;
import fi.jrnn.concisely.processor.discovery.ConciselyModelCreator;
import fi.jrnn.concisely.processor.generator.ConciselyGenerator;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Types;

import java.util.Set;

/**
 * @author Juho Juurinen
 */
@SupportedAnnotationTypes("fi.jrnn.concisely.annotation.Concisely")
public class ConciselyProcessor extends AbstractProcessor {

    private Filer filer;
    private Types types;

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.filer = processingEnv.getFiler();
        this.types = processingEnv.getTypeUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        var conciselyAnnotatedElements = annotations.stream()
                .flatMap(annotation -> roundEnv.getElementsAnnotatedWith(annotation).stream())
                .map(TypeElement.class::cast)
                .collect(toSet());

        debug(format("found elements annotated with @Concisely = %s", conciselyAnnotatedElements));

        if (conciselyAnnotatedElements.isEmpty()) {
            return false;
        }
        var conciselyAnnotatedClasses = modelCreator().createModel(conciselyAnnotatedElements);

        debug(format("created model = %s", conciselyAnnotatedClasses));

        new ConciselyGenerator(filer).generateSourceFiles(conciselyAnnotatedClasses);

        return false;
    }

    @VisibleForTesting
    ConciselyModelCreator modelCreator() {
        return new ConciselyModelCreator(types);
    }

    private void debug(String message) {
        processingEnv.getMessager().printMessage(NOTE, format("[%s] %s\n", getClass().getSimpleName(), message));
    }
}
