/*
 * Copyright (c) 2022 Juho Juurinen
 *
 * Licensed under the MIT license: https://opensource.org/licenses/MIT
 */
package fi.jrnn.concisely.processor.discovery;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isEmpty;
import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresentAnd;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verifyNoInteractions;

import fi.jrnn.concisely.processor.model.ConciselyAnnotatedClass;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

import java.util.Optional;
import java.util.Set;

/**
 * @author Juho Juurinen
 */
@DisplayName("ReferenceFinder")
@ExtendWith(MockitoExtension.class)
class TestReferenceFinder {

    private static final String KNOWN_QUALIFIED_NAME = "the.owls.are.not.what.they.seem.KnownClass";
    private static final String UNKNOWN_QUALIFIED_NAME = "the.owls.are.not.what.they.seem.UnknownClass";

    @Mock
    private Types types;

    @Mock
    private DeclaredType classType;

    @Mock
    private VariableElement propertyElement;

    @Mock
    private TypeMirror propertyType;

    @DisplayName("When calling 'findReference'")
    @Nested
    class WhenFindReference {

        private ConciselyAnnotatedClass conciselyClass;

        @BeforeEach
        void beforeEach(@Mock TypeElement classElement) {
            doReturn(KNOWN_QUALIFIED_NAME).when(classType).toString();
            doReturn(classType).when(classElement).asType();
            doReturn(propertyType).when(propertyElement).asType();
            conciselyClass = ConciselyAnnotatedClass.of(classElement);
        }

        @DisplayName("Given no Concisely class is found with the property's type name"
                + " Then should return an empty Optional")
        @Test
        void givenUnknownName() {
            doReturn(UNKNOWN_QUALIFIED_NAME).when(propertyType).toString();
            callAndAssertThat(isEmpty());
            verifyNoInteractions(types);
        }

        @DisplayName("Given a Concisely class is found with the property's type name")
        @Nested
        class GivenKnownName {

            @BeforeEach
            void beforeEach() {
                doReturn(KNOWN_QUALIFIED_NAME).when(propertyType).toString();
            }

            @DisplayName("And the Concisely and property types are not the same Then should return empty Optional")
            @Test
            void andDifferentTypes() {
                doReturn(false).when(types).isSameType(classType, propertyType);
                callAndAssertThat(isEmpty());
            }

            @DisplayName("And the Concisely and property types are the same Then should return that Concisely class")
            @Test
            void andSameTypes() {
                doReturn(true).when(types).isSameType(classType, propertyType);
                callAndAssertThat(isPresentAnd(equalTo(conciselyClass)));
            }
        }

        private void callAndAssertThat(Matcher<? super Optional<ConciselyAnnotatedClass>> matcher) {
            assertThat(new ReferenceFinder(types, Set.of(conciselyClass)).findReference(propertyElement), matcher);
        }
    }
}
