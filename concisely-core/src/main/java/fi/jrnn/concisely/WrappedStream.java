/*
 * Copyright (c) 2022 Juho Juurinen
 *
 * Licensed under the MIT license: https://opensource.org/licenses/MIT
 */
package fi.jrnn.concisely;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * Concisely implementations rely internally on {@link Stream}. It's possible to "break free" from Concisely at any
 * time by extracting the wrapped stream with {@link #stream()}.
 * <p>
 * For a minor convenience, a few common Stream methods are exposed, so that the transition to Stream API can be done
 * without an "extra" <code>.stream()</code> in between. E.g.
 * <pre>{@code
 *     concisely(foo)
 *             .bar()
 *             .flatMap( ... )
 * }</pre>
 * <p>
 * instead of
 * <pre>{@code
 *     concisely(foo)
 *             .bar()
 *             .stream()
 *             .flatMap( ... )
 * }</pre>
 * <p>
 * I don't know if anything from 1.9 or later can be included, if supported source code version in annotation
 * processing will be 1.8...?
 *
 * @author Juho Juurinen
 */
abstract class WrappedStream<T> {

    final Stream<T> stream;

    WrappedStream(Stream<T> stream) {
        this.stream = stream;
    }

    /**
     * Returns the contents of the current Concisely class as a {@link Stream}. Note that a number of Stream methods
     * are available readily (see e.g. {@link #map(Function)}, {@link #flatMap(Function)}, {@link #forEach(Consumer)}),
     * so this intermediate call might not be necessary.
     *
     * @return contents as stream
     */
    public Stream<T> stream() {
        return stream;
    }

    // TODO filter(Predicate) ?
    //      I'd prefer to incorporate filtering into the Concisely API itself, so this shouldn't be necessary

    /**
     * @see Stream#map(Function)
     */
    public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
        return stream.map(mapper);
    }

    /**
     * @see Stream#flatMap(Function)
     */
    public <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return stream.flatMap(mapper);
    }

    // TODO distinct() ?
    // TODO sorted() ?
    // TODO sorted(Comparator) ?
    // TODO peek() ?
    // TODO limit(long) ?
    // TODO skip(long) ?

    /**
     * @see Stream#forEach(Consumer)
     */
    public void forEach(Consumer<? super T> action) {
        stream.forEach(action);
    }

    /**
     * @see Stream#forEachOrdered(Consumer)
     */
    public void forEachOrdered(Consumer<? super T> action) {
        stream.forEachOrdered(action);
    }

    // TODO toArray() ?
    // TODO toArray(IntFunction) ?
    // TODO reduce(Object, BinaryOperator) ?
    // TODO reduce(BinaryOperator) ?
    // TODO reduce(Object, BiFunction, BinaryOperator) ?
    // TODO collect(Supplier, BiConsumer, BiConsumer) ?

    /**
     * @see Stream#collect(Collector)
     */
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        return stream.collect(collector);
    }

    // TODO min(Comparator) ?
    // TODO max(Comparator) ?
    // TODO count() ?

    /**
     * @see Stream#anyMatch(Predicate)
     */
    public boolean anyMatch(Predicate<? super T> predicate) {
        return stream.anyMatch(predicate);
    }

    /**
     * @see Stream#allMatch(Predicate)
     */
    public boolean allMatch(Predicate<? super T> predicate) {
        return stream.allMatch(predicate);
    }

    /**
     * @see Stream#noneMatch(Predicate)
     */
    public boolean noneMatch(Predicate<? super T> predicate) {
        return stream.noneMatch(predicate);
    }

    /**
     * @see Stream#findFirst()
     */
    public Optional<T> findFirst() {
        return stream.findFirst();
    }

    /**
     * @see Stream#findAny()
     */
    public Optional<T> findAny() {
        return stream.findAny();
    }
}
