/*
 * Copyright (c) 2022 Juho Juurinen
 *
 * Licensed under the MIT license: https://opensource.org/licenses/MIT
 */
package fi.jrnn.concisely.processor.model;

import fi.jrnn.concisely.annotation.Concisely;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a compile-time class annotated with {@link Concisely}.
 * <p>
 * For convenience, contains both the class {@code element} (the "blueprint"), and the {@code type} (the "invocation").
 * <p>
 * Since every class in scope must be unique, by extension, the encapsulated {@link TypeElement} must be unique, too.
 * Therefore, {@link #equals(Object)} and {@link #hashCode()} need not consider other properties than {@code element}.
 *
 * @author Juho Juurinen
 */
public class ConciselyAnnotatedClass {

    private final TypeElement element;
    private final DeclaredType type;
    private final Set<Reference> references = new HashSet<>();

    private ConciselyAnnotatedClass(TypeElement element) {
        this.element = element;
        this.type = (DeclaredType) element.asType();
    }

    public static ConciselyAnnotatedClass of(TypeElement element) {
        return new ConciselyAnnotatedClass(element);
    }

    public TypeElement getElement() {
        return element;
    }

    public DeclaredType getType() {
        return type;
    }

    public Set<Reference> getReferences() {
        return references;
    }

    public void addReferenceTo(ConciselyAnnotatedClass target) {
        references.add(Reference.of(this, target));
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
        var sb = new StringBuilder()
                .append("\n    ")
                .append(getElement());

        getReferences().forEach(reference -> sb
                .append("\n        refers to ")
                .append(reference.getTarget().getElement()));

        return sb.append("\n").toString();
    }
}
