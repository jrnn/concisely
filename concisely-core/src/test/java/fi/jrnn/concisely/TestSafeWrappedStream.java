package fi.jrnn.concisely;

import static fi.jrnn.concisely.SafeWrappedStream.safeStream;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@DisplayName("SafeWrappedStream")
class TestSafeWrappedStream {

    private final SomeClass someElement = new SomeClass();
    private final SomeClass anotherElement = new SomeClass();

    @DisplayName("When instantiating SafeWrappedStream")
    @Nested
    class WhenCreateNew {

        private SafeWrappedStream<SomeClass> safeWrappedStream;

        @DisplayName("Passing a Stream")
        @Nested
        class WithStream {

            private Stream<SomeClass> stream;

            @DisplayName("Given a null pointer Then the wrapped stream should be empty")
            @Test
            void givenNull() {
                stream = null;
                createAndAssertThat(empty());
            }

            @DisplayName("Given an empty Stream Then the wrapped stream should be empty")
            @Test
            void givenEmptyStream() {
                stream = Stream.empty();
                createAndAssertThat(empty());
            }

            @DisplayName("Given a Stream with elements Then the wrapped stream should contain those elements")
            @Test
            void givenStream() {
                stream = Stream.of(someElement, anotherElement);
                createAndAssertThat(containsInAnyOrder(someElement, anotherElement));
            }

            @DisplayName("Given a Stream with elements and a null pointer"
                    + " Then the wrapped stream should contain only the elements")
            @Test
            void givenStreamWithNull() {
                stream = Stream.of(someElement, null, anotherElement);
                createAndAssertThat(containsInAnyOrder(someElement, anotherElement));
            }

            private void createAndAssertThat(Matcher<? super Collection<? super SomeClass>> expected) {
                safeWrappedStream = new SafeWrappedStream<>(stream) {
                };
                assertThat(safeWrappedStream.stream().toList(), expected);
            }
        }

        @DisplayName("Passing an object")
        @Nested
        class WithObject {

            private SomeClass object;

            @DisplayName("Given a null pointer Then the wrapped stream should be empty")
            @Test
            void givenNull() {
                object = null;
                createAndAssertThat(empty());
            }

            @DisplayName("Given an instance Then the wrapped stream should contain that instance")
            @Test
            void givenInstance() {
                object = someElement;
                createAndAssertThat(contains(someElement));
            }

            private void createAndAssertThat(Matcher<? super Collection<? super SomeClass>> expected) {
                safeWrappedStream = new SafeWrappedStream<>(object) {
                };
                assertThat(safeWrappedStream.stream().toList(), expected);
            }
        }

        @DisplayName("Passing a Collection")
        @Nested
        class WithCollection {

            private Collection<SomeClass> collection;

            @DisplayName("Given a null pointer Then the wrapped stream should be empty")
            @Test
            void givenNull() {
                collection = null;
                createAndAssertThat(empty());
            }

            @DisplayName("Given an empty Collection Then the wrapped stream should be empty")
            @Test
            void givenEmptyCollection() {
                collection = List.of();
                createAndAssertThat(empty());
            }

            @DisplayName("Given a Collection with elements Then the wrapped stream should contain those elements")
            @Test
            void givenCollection() {
                collection = List.of(someElement, anotherElement);
                createAndAssertThat(containsInAnyOrder(someElement, anotherElement));
            }

            @DisplayName("Given a Collection with elements and a null pointer"
                    + " Then the wrapped stream should contain only the elements")
            @Test
            void givenCollectionWithNull() {
                collection = new ArrayList<>();
                collection.add(someElement);
                collection.add(null);
                collection.add(anotherElement);
                createAndAssertThat(containsInAnyOrder(someElement, anotherElement));
            }

            private void createAndAssertThat(Matcher<? super Collection<? super SomeClass>> expected) {
                safeWrappedStream = new SafeWrappedStream<>(collection) {
                };
                assertThat(safeWrappedStream.stream().toList(), expected);
            }
        }

        @DisplayName("Passing an Optional")
        @Nested
        class WithOptional {

            private Optional<SomeClass> optional;

            @DisplayName("Given a null pointer Then the wrapped stream should be empty")
            @Test
            void givenNull() {
                optional = null;
                createAndAssertThat(empty());
            }

            @DisplayName("Given an empty Optional Then the wrapped stream should be empty")
            @Test
            void givenEmptyOptional() {
                optional = Optional.empty();
                createAndAssertThat(empty());
            }

            @DisplayName("Given an Optional with an element Then the wrapped stream should contain that element")
            @Test
            void givenOptional() {
                optional = Optional.of(someElement);
                createAndAssertThat(contains(someElement));
            }

            private void createAndAssertThat(Matcher<? super Collection<? super SomeClass>> expected) {
                safeWrappedStream = new SafeWrappedStream<>(optional) {
                };
                assertThat(safeWrappedStream.stream().toList(), expected);
            }
        }
    }

    @DisplayName("When calling 'safeStream'")
    @Nested
    class WhenSafeStream {

        @DisplayName("Passing a Stream")
        @Nested
        class WithStream {

            private Stream<SomeClass> stream;

            @DisplayName("Given a null pointer Then should return an empty Stream")
            @Test
            void givenNull() {
                stream = null;
                callAndAssertThat(empty());
            }

            @DisplayName("Given an empty Stream Then should return an empty Stream")
            @Test
            void givenEmptyStream() {
                stream = Stream.empty();
                callAndAssertThat(empty());
            }

            @DisplayName("Given a Stream with elements Then should return a Stream containing those elements")
            @Test
            void givenStream() {
                stream = Stream.of(someElement, anotherElement);
                callAndAssertThat(containsInAnyOrder(someElement, anotherElement));
            }

            @DisplayName("Given a Stream with elements and a null pointer"
                    + " Then should return a Stream containing only the elements")
            @Test
            void givenStreamWithNull() {
                stream = Stream.of(someElement, null, anotherElement);
                callAndAssertThat(containsInAnyOrder(someElement, anotherElement));
            }

            private void callAndAssertThat(Matcher<? super Collection<? super SomeClass>> expected) {
                assertThat(safeStream(stream).toList(), expected);
            }
        }

        @DisplayName("Passing an object")
        @Nested
        class WithObject {

            private SomeClass object;

            @DisplayName("Given a null pointer Then should return an empty Stream")
            @Test
            void givenNull() {
                object = null;
                callAndAssertThat(empty());
            }

            @DisplayName("Given an instance Then should return a Stream containing that instance")
            @Test
            void givenInstance() {
                object = someElement;
                callAndAssertThat(contains(someElement));
            }

            private void callAndAssertThat(Matcher<? super Collection<? super SomeClass>> expected) {
                assertThat(safeStream(object).toList(), expected);
            }
        }

        @DisplayName("Passing a Collection")
        @Nested
        class WithCollection {

            private Collection<SomeClass> collection;

            @DisplayName("Given a null pointer Then should return an empty Stream")
            @Test
            void givenNull() {
                collection = null;
                callAndAssertThat(empty());
            }

            @DisplayName("Given an empty Collection Then should return an empty Stream")
            @Test
            void givenEmptyCollection() {
                collection = List.of();
                callAndAssertThat(empty());
            }

            @DisplayName("Given a Collection with elements Then should return a Stream containing those elements")
            @Test
            void givenCollection() {
                collection = List.of(someElement, anotherElement);
                callAndAssertThat(containsInAnyOrder(someElement, anotherElement));
            }

            @DisplayName("Given a Collection with elements and a null pointer"
                    + " Then should return a Stream containing only the elements")
            @Test
            void givenCollectionWithNull() {
                collection = new ArrayList<>();
                collection.add(someElement);
                collection.add(null);
                collection.add(anotherElement);
                callAndAssertThat(containsInAnyOrder(someElement, anotherElement));
            }

            private void callAndAssertThat(Matcher<? super Collection<? super SomeClass>> expected) {
                assertThat(safeStream(collection).toList(), expected);
            }
        }

        @DisplayName("Passing an Optional")
        @Nested
        class WithOptional {

            private Optional<SomeClass> optional;

            @DisplayName("Given a null pointer Then should return an empty Stream")
            @Test
            void givenNull() {
                optional = null;
                callAndAssertThat(empty());
            }

            @DisplayName("Given an empty Optional Then should return an empty Stream")
            @Test
            void givenEmptyOptional() {
                optional = Optional.empty();
                callAndAssertThat(empty());
            }

            @DisplayName("Given an Optional with an element Then should return a Stream containing that element")
            @Test
            void givenOptional() {
                optional = Optional.of(someElement);
                callAndAssertThat(contains(someElement));
            }

            private void callAndAssertThat(Matcher<? super Collection<? super SomeClass>> expected) {
                assertThat(safeStream(optional).toList(), expected);
            }
        }
    }

    private static class SomeClass {
    }
}
