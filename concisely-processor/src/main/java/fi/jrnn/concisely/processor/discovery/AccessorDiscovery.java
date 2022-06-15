/*
 * Copyright (c) 2022 Juho Juurinen
 *
 * Licensed under the MIT license: https://opensource.org/licenses/MIT
 */
package fi.jrnn.concisely.processor.discovery;

import static javax.lang.model.element.ElementKind.METHOD;

import fi.jrnn.concisely.processor.model.ConciselyAnnotatedClass;
import fi.jrnn.concisely.processor.model.Reference;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.util.Types;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

/**
 * Finds and adds accessors to references.
 * <p>
 * Only methods are considered "accessors". The idea is to look for a getter in the source class that returns the
 * target class. The method need not follow the {@code getSomething()} naming convention.
 * <p>
 * There could be other ways too, such as directly accessing class properties if they are public. But that just goes so
 * heavily against the grain of convention that it's out of the question.
 *
 * @author Juho Juurinen
 */
class AccessorDiscovery {

    private final Types types;

    AccessorDiscovery(Types types) {
        this.types = types;
    }

    void addAccessors(Set<ConciselyAnnotatedClass> conciselyAnnotatedClasses) {
        var findAccessor = accessorFinder(types);
        conciselyAnnotatedClasses.stream()
                .flatMap(c -> c.getReferences().stream())
                .forEach(reference -> findAccessor.apply(reference)
                        .ifPresent(reference::setAccessor));
    }

    private static Function<Reference, Optional<ExecutableElement>> accessorFinder(Types types) {
        return reference -> {
            var sourceElement = reference.getSource().getElement();
            var targetType = reference.getTarget().getType();
            return sourceElement.getEnclosedElements().stream()
                    .filter(enclosedElement -> enclosedElement.getKind() == METHOD)
                    .map(ExecutableElement.class::cast)
                    .filter(methodElement -> types.isSameType(methodElement.getReturnType(), targetType))
                    .findFirst();
        };
    }
}
