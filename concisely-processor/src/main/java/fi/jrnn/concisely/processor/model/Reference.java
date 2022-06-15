/*
 * Copyright (c) 2022 Juho Juurinen
 *
 * Licensed under the MIT license: https://opensource.org/licenses/MIT
 */
package fi.jrnn.concisely.processor.model;

import javax.lang.model.element.ExecutableElement;

import java.util.Objects;
import java.util.Optional;

/**
 * Represents a relation between two Concisely classes.
 * <p>
 * Should tell how the {@code source} class accesses the {@code target} class with {@link #getAccessor()}. It's assumed
 * that there can be no more than one reference between certain classes, so the accessor is not taken into account in
 * {@link #equals(Object)} and {@link #hashCode()}.
 * <p>
 * Conceptually, maybe the closest counterpart are cardinalities e.g. one-to-one, one-to-many, etc.
 * <p>
 * Corresponds to an arc (or, directed edge) in a graph whose vertices are {@link ConciselyAnnotatedClass}.
 *
 * @author Juho Juurinen
 */
public class Reference {

    private final ConciselyAnnotatedClass source;
    private final ConciselyAnnotatedClass target;
    private ExecutableElement accessor;

    private Reference(ConciselyAnnotatedClass source, ConciselyAnnotatedClass target) {
        this.source = source;
        this.target = target;
    }

    public static Reference of(ConciselyAnnotatedClass source, ConciselyAnnotatedClass target) {
        return new Reference(source, target);
    }

    public ConciselyAnnotatedClass getSource() {
        return source;
    }

    public ConciselyAnnotatedClass getTarget() {
        return target;
    }

    public Optional<ExecutableElement> getAccessor() {
        return Optional.ofNullable(accessor);
    }

    public void setAccessor(ExecutableElement accessor) {
        this.accessor = accessor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        var other = (Reference) o;
        return Objects.equals(getSource(), other.getSource())
                && Objects.equals(getTarget(), other.getTarget());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSource(), getTarget());
    }
}
