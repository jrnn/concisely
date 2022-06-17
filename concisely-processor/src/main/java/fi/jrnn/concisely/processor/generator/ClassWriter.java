/*
 * Copyright (c) 2022 Juho Juurinen
 *
 * Licensed under the MIT license: https://opensource.org/licenses/MIT
 */
package fi.jrnn.concisely.processor.generator;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

import fi.jrnn.concisely.processor.model.ConciselyAnnotatedClass;
import fi.jrnn.concisely.processor.model.Reference;

import javax.annotation.processing.Filer;

import java.io.IOException;
import java.util.stream.Stream;

/**
 * @author Juho Juurinen
 */
class ClassWriter {

    private static final String DEFAULT_PACKAGE_NAME = "fi.jrnn.concisely";
    private static final String ABSTRACT_TEMPLATE = """
            package %s;

            %s

            import java.util.Collection;
            import java.util.Optional;
            import java.util.stream.Stream;

            abstract class %s<T extends %s> extends AbstractConcisely<T> {

                %s(Stream<T> stream) {
                    super(stream);
                }

                %s(T t) {
                    super(t);
                }

                %s(Collection<T> ts) {
                    super(ts);
                }

                %s(Optional<T> maybeT) {
                    super(maybeT);
                }
            %s}
            """;

    private static final String PUBLIC_TEMPLATE = """
            package %s;

            import %s;

            import java.util.Collection;
            import java.util.Optional;
            import java.util.stream.Stream;

            public class %s extends Abstract%s {

                public %s(Stream<%s> stream) {
                    super(stream);
                }

                public %s(%s t) {
                    super(t);
                }

                public %s(Collection<%s> ts) {
                    super(ts);
                }

                public %s(Optional<%s> maybeT) {
                    super(maybeT);
                }
            }
            """;

    private static final String ACCESSOR_METHOD_TEMPLATE = """

                private Stream<%s> to%sStream() {
                    return stream.flatMap(t -> safeStream(t.%s));
                }

                public %sConcisely %s() {
                    return new %sConcisely(to%sStream());
                }
            """;

    private final Filer filer;

    ClassWriter(Filer filer) {
        this.filer = filer;
    }

    void generate(ConciselyAnnotatedClass conciselyClass) throws IOException {
        var className = getSimpleName(conciselyClass);
        var abstractClassName = format("Abstract%sConcisely", upperFirst(className));
        var abstractQualifiedName = format("%s.%s", DEFAULT_PACKAGE_NAME, abstractClassName);
        var abstractConcisely = filer.createSourceFile(abstractQualifiedName);

        try (var writer = abstractConcisely.openWriter()) {
            writer.write(format(ABSTRACT_TEMPLATE,
                    DEFAULT_PACKAGE_NAME,
                    toImportsString(conciselyClass),
                    abstractClassName, className,
                    abstractClassName, abstractClassName, abstractClassName, abstractClassName,
                    toMethodsString(conciselyClass)
            ));
        }
        var publicClassName = format("%sConcisely", upperFirst(className));
        var publicQualifiedName = format("%s.%s", DEFAULT_PACKAGE_NAME, publicClassName);
        var publicConcisely = filer.createSourceFile(publicQualifiedName);

        try (var writer = publicConcisely.openWriter()) {
            writer.write(format(PUBLIC_TEMPLATE,
                    DEFAULT_PACKAGE_NAME,
                    toQualifiedName(conciselyClass),
                    publicClassName, publicClassName,
                    publicClassName, className,
                    publicClassName, className,
                    publicClassName, className,
                    publicClassName, className
            ));
        }
    }

    private static String getSimpleName(ConciselyAnnotatedClass conciselyClass) {
        var qualifiedName = conciselyClass.getElement().getQualifiedName().toString();
        return qualifiedName.substring(qualifiedName.lastIndexOf('.') + 1);
    }

    private static String upperFirst(String s) {
        var firstLetter = s.substring(0, 1).toUpperCase();
        return format("%s%s", firstLetter, s.substring(1));
    }

    private static String lowerFirst(String s) {
        var firstLetter = s.substring(0, 1).toLowerCase();
        return format("%s%s", firstLetter, s.substring(1));
    }

    private static String toQualifiedName(ConciselyAnnotatedClass conciselyClass) {
        return conciselyClass.getElement().getQualifiedName().toString();
    }

    private static String toImportsString(ConciselyAnnotatedClass conciselyClass) {
        return Stream.concat(
                        Stream.of(toQualifiedName(conciselyClass)),
                        conciselyClass.getReferences().stream()
                                .map(reference -> toQualifiedName(reference.getTarget())))
                .sorted()
                .map(name -> format("import %s;", name))
                .collect(joining("\n"));
    }

    private static String toMethodsString(ConciselyAnnotatedClass conciselyClass) {
        return conciselyClass.getReferences().stream()
                .map(ClassWriter::toMethodString)
                .collect(joining());
    }

    private static String toMethodString(Reference reference) {
        var targetClassName = getSimpleName(reference.getTarget());
        return format(ACCESSOR_METHOD_TEMPLATE,
                targetClassName, upperFirst(targetClassName),
                reference.getAccessor().orElseThrow(),
                targetClassName, lowerFirst(targetClassName),
                targetClassName, targetClassName
        );
    }
}
