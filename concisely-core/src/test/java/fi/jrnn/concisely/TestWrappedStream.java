/*
 * Copyright (c) 2022 Juho Juurinen
 *
 * Licensed under the MIT license: https://opensource.org/licenses/MIT
 */
package fi.jrnn.concisely;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresentAndIs;
import static fi.jrnn.concisely.TestUtil.randomBoolean;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * @author Juho Juurinen
 */
@ExtendWith(MockitoExtension.class)
class TestWrappedStream {

    private WrappedStream<SomeClass> wrappedStream;

    @Mock
    private Stream<SomeClass> stream;

    @BeforeEach
    void beforeEach() {
        wrappedStream = new WrappedStream<>(stream) {
        };
    }

    @DisplayName("When calling 'stream' Then returns the wrapped stream")
    @Test
    void whenStream() {
        assertThat(wrappedStream.stream(), is(stream));
    }

    @DisplayName("When calling 'map' Then should call the wrapped stream's 'map' And return the resulting stream")
    @Test
    void whenMap(@Mock Function<SomeClass, AnotherClass> mapper, @Mock Stream<AnotherClass> mappedStream) {
        doReturn(mappedStream).when(stream).map(mapper);
        assertThat(wrappedStream.map(mapper), is(mappedStream));
    }

    @DisplayName("When calling 'flatMap'"
            + " Then should call the wrapped stream's 'flatMap' And return the resulting stream")
    @Test
    void whenFlatMap(@Mock Function<SomeClass, Stream<AnotherClass>> mapper, @Mock Stream<AnotherClass> mappedStream) {
        doReturn(mappedStream).when(stream).flatMap(mapper);
        assertThat(wrappedStream.flatMap(mapper), is(mappedStream));
    }

    @DisplayName("When calling 'forEach' Then should call the wrapped stream's 'forEach' And terminate")
    @Test
    void whenForEach(@Mock Consumer<SomeClass> action) {
        wrappedStream.forEach(action);
        verify(stream).forEach(action);
    }

    @DisplayName("When calling 'forEachOrdered' Then should call the wrapped stream's 'forEachOrdered' And terminate")
    @Test
    void whenForEachOrdered(@Mock Consumer<SomeClass> action) {
        wrappedStream.forEachOrdered(action);
        verify(stream).forEachOrdered(action);
    }

    @DisplayName("When calling 'collect'"
            + " Then should call the wrapped stream's 'collect' And return the resulting reduction")
    @Test
    void whenCollect(@Mock Collector<SomeClass, ?, Set<SomeClass>> collector, @Mock Set<SomeClass> reduction) {
        doReturn(reduction).when(stream).collect(collector);
        assertThat(wrappedStream.collect(collector), is(reduction));
    }

    @DisplayName("When calling 'anyMatch' Then should call the wrapped stream's 'anyMatch' And return the result")
    @Test
    void whenAnyMatch(@Mock Predicate<SomeClass> predicate) {
        var result = randomBoolean();
        doReturn(result).when(stream).anyMatch(predicate);
        assertThat(wrappedStream.anyMatch(predicate), is(result));
    }

    @DisplayName("When calling 'allMatch' Then should call the wrapped stream's 'allMatch' And return the result")
    @Test
    void whenAllMatch(@Mock Predicate<SomeClass> predicate) {
        var result = randomBoolean();
        doReturn(result).when(stream).allMatch(predicate);
        assertThat(wrappedStream.allMatch(predicate), is(result));
    }

    @DisplayName("When calling 'noneMatch' Then should call the wrapped stream's 'noneMatch' And return the result")
    @Test
    void whenNoneMatch(@Mock Predicate<SomeClass> predicate) {
        var result = randomBoolean();
        doReturn(result).when(stream).noneMatch(predicate);
        assertThat(wrappedStream.noneMatch(predicate), is(result));
    }

    @DisplayName("When calling 'findFirst'"
            + " Then should call the wrapped stream's 'findFirst' And return the resulting optional")
    @Test
    void whenFindFirst(@Mock SomeClass first) {
        doReturn(Optional.of(first)).when(stream).findFirst();
        assertThat(wrappedStream.findFirst(), isPresentAndIs(first));
    }

    @DisplayName("When calling 'findAny'"
            + " Then should call the wrapped stream's 'findAny' And return the resulting optional")
    @Test
    void whenFindAny(@Mock SomeClass any) {
        doReturn(Optional.of(any)).when(stream).findAny();
        assertThat(wrappedStream.findAny(), isPresentAndIs(any));
    }

    private static class SomeClass {
    }

    private static class AnotherClass {
    }
}
