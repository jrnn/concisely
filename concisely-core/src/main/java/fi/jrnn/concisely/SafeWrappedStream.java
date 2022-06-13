/*
 * Copyright (c) 2022 Juho Juurinen
 *
 * Licensed under the MIT license: https://opensource.org/licenses/MIT
 */
package fi.jrnn.concisely;

import static java.util.function.Function.identity;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Concisely implementations need to be bulletproof against {@link NullPointerException}s, to ensure they can work with
 * all kinds of trash POJOs that e.g. do not initialize empty collections. Therefore {@link WrappedStream} gets an
 * extra layer for "safe access" that should handle possible null pointers, and/or null elements in collections and
 * streams.
 * <p>
 * The static methods are available via inheritance, and should be utilized by generated classes. Overloading should
 * simplify the usage of these methods in generated code -- no need to discern what is being passed as a parameter,
 * apart from making sure it's a recognized type.
 *
 * @author Juho Juurinen
 */
abstract class SafeWrappedStream<T> extends WrappedStream<T> {

    SafeWrappedStream(Stream<T> stream) {
        super(safeStream(stream));
    }

    SafeWrappedStream(T t) {
        super(safeStream(t));
    }

    SafeWrappedStream(Collection<T> ts) {
        super(safeStream(ts));
    }

    SafeWrappedStream(Optional<T> maybeT) {
        super(safeStream(maybeT));
    }

    static <U> Stream<U> safeStream(Stream<U> stream) {
        return Stream
                .ofNullable(stream)
                .flatMap(identity())
                .filter(Objects::nonNull);
    }

    static <U> Stream<U> safeStream(U u) {
        return Stream.ofNullable(u);
    }

    static <U> Stream<U> safeStream(Collection<U> us) {
        return Stream
                .ofNullable(us)
                .flatMap(Collection::stream)
                .filter(Objects::nonNull);
    }

    static <U> Stream<U> safeStream(Optional<U> maybeU) {
        return Stream
                .ofNullable(maybeU)
                .flatMap(Optional::stream);
    }
}
