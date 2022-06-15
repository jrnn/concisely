/*
 * Copyright (c) 2022 Juho Juurinen
 *
 * Licensed under the MIT license: https://opensource.org/licenses/MIT
 */
package fi.jrnn.concisely.processor.discovery;

import static javax.lang.model.element.ElementKind.FIELD;

import fi.jrnn.concisely.VisibleForTesting;
import fi.jrnn.concisely.processor.model.ConciselyAnnotatedClass;

import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Types;

import java.util.Set;
import java.util.function.Consumer;

/**
 * Finds and maps references between Concisely classes.
 * <p>
 * The idea is to look for references via class properties. By convention, most domain classes just are built that way
 * (e.g. JavaBeans and Entities): if, for example, {@code Foo} has a one-to-many relationship to {@code Bar}, you're
 * very likely to find {@code private Set<Bar> bars;} in {@code Foo}, and {@code private Foo foo;} in {@code Bar}. So,
 * this should work for the majority of cases.
 * <p>
 * There could be other alternatives, too, but let's not sweat about it now.
 *
 * @author Juho Juurinen
 */
class ReferenceDiscovery {

    private final Types types;

    ReferenceDiscovery(Types types) {
        this.types = types;
    }

    void mapReferences(Set<ConciselyAnnotatedClass> conciselyAnnotatedClasses) {
        var referenceFinder = referenceFinder(conciselyAnnotatedClasses);
        var mapReferences = referenceMapper(referenceFinder);
        conciselyAnnotatedClasses.forEach(mapReferences);
    }

    private static Consumer<ConciselyAnnotatedClass> referenceMapper(ReferenceFinder referenceFinder) {
        return c -> c.getElement().getEnclosedElements().stream()
                .filter(enclosedElement -> enclosedElement.getKind() == FIELD)
                .map(VariableElement.class::cast)
                .flatMap(propertyElement -> referenceFinder.findReference(propertyElement).stream())
                .forEach(c::addReferenceTo);
    }

    @VisibleForTesting
    ReferenceFinder referenceFinder(Set<ConciselyAnnotatedClass> conciselyAnnotatedClasses) {
        return new ReferenceFinder(types, conciselyAnnotatedClasses);
    }
}
