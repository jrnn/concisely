/*
 * Copyright (c) 2022 Juho Juurinen
 *
 * Licensed under the MIT license: https://opensource.org/licenses/MIT
 */
package fi.jrnn.concisely.processor.model;

import java.util.Objects;

/**
 * Represents a relation between two Concisely classes.
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
