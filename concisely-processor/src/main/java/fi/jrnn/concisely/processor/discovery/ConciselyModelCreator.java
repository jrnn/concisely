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

import java.util.Set;

/**
 * Creates a model from classes annotated with {@link Concisely}, based on which the output will be generated.
 *
 * @author Juho Juurinen
 */
public class ConciselyModelCreator {

    public Set<ConciselyAnnotatedClass> createModel(Set<TypeElement> conciselyAnnotatedElements) {
        return conciselyAnnotatedElements.stream()
                .map(ConciselyAnnotatedClass::of)
                .collect(toSet());
    }
}
