/*
 * Copyright (c) 2022 Juho Juurinen
 *
 * Licensed under the MIT license: https://opensource.org/licenses/MIT
 */
package fi.jrnn.concisely.processor.discovery;

import static fi.jrnn.concisely.TestUtil.randomEnumExcluding;
import static javax.lang.model.element.ElementKind.FIELD;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verifyNoInteractions;

import fi.jrnn.concisely.processor.model.ConciselyAnnotatedClass;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Juho Juurinen
 */
@DisplayName("ReferenceDiscovery")
@ExtendWith(MockitoExtension.class)
class TestReferenceDiscovery {

    @Spy
    @InjectMocks
    private ReferenceDiscovery referenceDiscovery;

    @Mock
    private ReferenceFinder referenceFinder;

    @Mock
    private VariableElement enclosedElement;

    @DisplayName("When calling 'mapReferences'")
    @Nested
    class WhenMapReferences {

        private ConciselyAnnotatedClass conciselyClass;
        private Set<ConciselyAnnotatedClass> conciselyClasses;

        @BeforeEach
        void beforeEach(@Mock TypeElement classElement) {
            conciselyClass = ConciselyAnnotatedClass.of(classElement);
            conciselyClasses = Set.of(conciselyClass);
            doReturn(List.of(enclosedElement)).when(classElement).getEnclosedElements();
            doReturn(referenceFinder).when(referenceDiscovery).referenceFinder(conciselyClasses);
        }

        @DisplayName("Given a Concisely class has no enclosed fields Then should not add any references")
        @Test
        void givenNoEnclosedFields() {
            doReturn(randomEnumExcluding(ElementKind.class, FIELD)).when(enclosedElement).getKind();
            referenceDiscovery.mapReferences(conciselyClasses);
            assertThat(conciselyClass.getReferences(), empty());
            verifyNoInteractions(referenceFinder);
        }

        @DisplayName("Given a Concisely class has an enclosed field")
        @Nested
        class GivenEnclosedField {

            @BeforeEach
            void beforeEach() {
                doReturn(FIELD).when(enclosedElement).getKind();
            }

            @DisplayName("Which does not refer to a Concisely class Then should not add any references")
            @Test
            void andNoMatch() {
                doReturn(Optional.empty()).when(referenceFinder).findReference(enclosedElement);
                referenceDiscovery.mapReferences(conciselyClasses);
                assertThat(conciselyClass.getReferences(), empty());
            }

            @DisplayName("Which refers to a Concisely class Then should add a reference to that class")
            @Test
            void andMatch(@Mock TypeElement targetElement) {
                var targetClass = ConciselyAnnotatedClass.of(targetElement);
                doReturn(Optional.of(targetClass)).when(referenceFinder).findReference(enclosedElement);

                referenceDiscovery.mapReferences(conciselyClasses);

                assertThat(conciselyClass.getReferences(), hasSize(1));
                conciselyClass.getReferences().forEach(reference
                        -> assertThat(reference.getTarget(), equalTo(targetClass)));
            }
        }
    }
}
