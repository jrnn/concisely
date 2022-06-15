/*
 * Copyright (c) 2022 Juho Juurinen
 *
 * Licensed under the MIT license: https://opensource.org/licenses/MIT
 */
package fi.jrnn.concisely.processor.model;

import fi.jrnn.concisely.annotation.Concisely;

import javax.lang.model.element.TypeElement;

import java.util.Objects;

/**
 * Represents a compile-time class annotated with {@link Concisely}.
 * <p>
 * Since every class in scope must be unique, by extension, the enclosed {@link TypeElement} must be unique, too.
 * Therefore, {@link #equals(Object)} and {@link #hashCode()} need not consider other properties than {@code element}.
 *
 * @author Juho Juurinen
 */
public class ConciselyAnnotatedClass {

    private final TypeElement element;

    private ConciselyAnnotatedClass(TypeElement element) {
        this.element = element;
    }

    public static ConciselyAnnotatedClass of(TypeElement element) {
        return new ConciselyAnnotatedClass(element);
    }

    public TypeElement getElement() {
        return element;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        var other = (ConciselyAnnotatedClass) o;
        return Objects.equals(getElement(), other.getElement());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getElement());
    }

    // TODO temporary BS for debugging ...
    @Override
    public String toString() {
        return getElement().toString();
    }
}
