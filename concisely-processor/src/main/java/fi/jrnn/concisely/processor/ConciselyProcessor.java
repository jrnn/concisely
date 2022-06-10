package fi.jrnn.concisely.processor;

import static java.util.stream.Collectors.toSet;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Set;

@SupportedAnnotationTypes("fi.jrnn.concisely.annotation.Concisely")
public class ConciselyProcessor extends AbstractProcessor {

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        var conciselyElements = annotations.stream()
                .flatMap(annotation -> roundEnv.getElementsAnnotatedWith(annotation).stream())
                .map(TypeElement.class::cast)
                .collect(toSet());

        System.out.printf("[%s] found classes annotated with @Concisely = %s\n",
                getClass().getSimpleName(), conciselyElements);

        return false;
    }
}
