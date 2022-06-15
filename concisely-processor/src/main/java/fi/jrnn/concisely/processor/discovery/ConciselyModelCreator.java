/*
 * Copyright (c) 2022 Juho Juurinen
 *
 * Licensed under the MIT license: https://opensource.org/licenses/MIT
 */
package fi.jrnn.concisely.processor.discovery;

import static java.util.stream.Collectors.toSet;

import fi.jrnn.concisely.annotation.Concisely;
import fi.jrnn.concisely.processor.model.ConciselyAnnotatedClass;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Types;

import java.util.Set;

/**
 * Creates a model from classes annotated with {@link Concisely}, based on which the output will be generated.
 * <p>
 * The produced model needs to depict how the classes in scope relate to one another (here called "references").
 * <p>
 * Coincidentally the model takes the form of a directed graph, though there's nothing going on here which would take
 * advantage of such organization (e.g. BFS, DFS, strongly connected components, what have you...) But who knows, maybe
 * something turns up later.
 *
 * @author Juho Juurinen
 */
public class ConciselyModelCreator {

    private final Types types;

    public ConciselyModelCreator(Types types) {
        this.types = types;
    }

    public Set<ConciselyAnnotatedClass> createModel(Set<TypeElement> conciselyAnnotatedElements) {
        var conciselyAnnotatedClasses = conciselyAnnotatedElements.stream()
                .map(ConciselyAnnotatedClass::of)
                .collect(toSet());

        new ReferenceDiscovery(types).mapReferences(conciselyAnnotatedClasses);

        return conciselyAnnotatedClasses;
    }
}
