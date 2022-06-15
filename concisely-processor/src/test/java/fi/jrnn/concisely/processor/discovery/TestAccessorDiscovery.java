/*
 * Copyright (c) 2022 Juho Juurinen
 *
 * Licensed under the MIT license: https://opensource.org/licenses/MIT
 */
package fi.jrnn.concisely.processor.discovery;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isEmpty;
import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresentAndIs;
import static fi.jrnn.concisely.TestUtil.randomEnumExcluding;
import static javax.lang.model.element.ElementKind.METHOD;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verifyNoInteractions;

import fi.jrnn.concisely.processor.model.ConciselyAnnotatedClass;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.Types;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Juho Juurinen
 */
@DisplayName("AccessorDiscovery")
@ExtendWith(MockitoExtension.class)
class TestAccessorDiscovery {

    @InjectMocks
    private AccessorDiscovery accessorDiscovery;

    @Mock
    private Types types;

    @Mock
    private ExecutableElement enclosedElement;

    @Mock
    private DeclaredType targetType;

    @DisplayName("When calling 'addAccessors'")
    @Nested
    class WhenAddAccessors {

        private ConciselyAnnotatedClass sourceClass;

        @BeforeEach
        void beforeEach(@Mock TypeElement sourceElement, @Mock TypeElement targetElement) {
            doReturn(List.of(enclosedElement)).when(sourceElement).getEnclosedElements();
            doReturn(targetType).when(targetElement).asType();
            sourceClass = ConciselyAnnotatedClass.of(sourceElement);
            sourceClass.addReferenceTo(ConciselyAnnotatedClass.of(targetElement));
        }

        @DisplayName("Given a Concisely class has no enclosed methods Then should not add any accessors")
        @Test
        void givenNoEnclosedMethods() {
            doReturn(randomEnumExcluding(ElementKind.class, METHOD)).when(enclosedElement).getKind();
            callAndAssertAccessor(isEmpty());
            verifyNoInteractions(types);
        }

        @DisplayName("Given a Concisely class has an enclosed method")
        @Nested
        class GivenEnclosedMethod {

            @Mock
            private DeclaredType enclosedReturnType;

            @BeforeEach
            void beforeEach() {
                doReturn(METHOD).when(enclosedElement).getKind();
                doReturn(enclosedReturnType).when(enclosedElement).getReturnType();
            }

            @DisplayName("Which does not return the referred target type Then should not add any accessors")
            @Test
            void andNoMatch() {
                doReturn(false).when(types).isSameType(enclosedReturnType, targetType);
                callAndAssertAccessor(isEmpty());
            }

            @DisplayName("Which returns the referred target type"
                    + " Then should set that method as the reference's accessor")
            @Test
            void andMatch() {
                doReturn(true).when(types).isSameType(enclosedReturnType, targetType);
                callAndAssertAccessor(isPresentAndIs(enclosedElement));
            }
        }

        private void callAndAssertAccessor(Matcher<? super Optional<ExecutableElement>> matcher) {
            accessorDiscovery.addAccessors(Set.of(sourceClass));
            assertThat(sourceClass.getReferences(), hasSize(1));
            sourceClass.getReferences().forEach(reference -> assertThat(reference.getAccessor(), matcher));
        }
    }
}
