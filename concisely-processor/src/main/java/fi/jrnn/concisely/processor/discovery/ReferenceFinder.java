/*
 * Copyright (c) 2022 Juho Juurinen
 *
 * Licensed under the MIT license: https://opensource.org/licenses/MIT
 */
package fi.jrnn.concisely.processor.discovery;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import fi.jrnn.concisely.processor.model.ConciselyAnnotatedClass;

import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Types;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Looks at a given {@link VariableElement}'s type, and if that type is a Concisely class, then returns the
 * corresponding {@link ConciselyAnnotatedClass}.
 * <p>
 * Or, simply put: given a class property, tells which Concisely class it "refers" to, if any.
 *
 * @author Juho Juurinen
 */
class ReferenceFinder {

    private final Types types;
    private final Map<String, ConciselyAnnotatedClass> conciselyClassesByName;

    ReferenceFinder(Types types, Set<ConciselyAnnotatedClass> conciselyAnnotatedClasses) {
        this.types = types;
        this.conciselyClassesByName = conciselyAnnotatedClasses.stream()
                .collect(toMap(c -> c.getType().toString(), identity()));
    }

    Optional<ConciselyAnnotatedClass> findReference(VariableElement propertyElement) {
        var propertyType = propertyElement.asType();
        return Optional
                // TODO okay, this is pretty sketchy...?! oh well
                .of(propertyType.toString())
                .map(conciselyClassesByName::get)
                .filter(c -> types.isSameType(c.getType(), propertyType));
    }
}
