/*
 * Copyright (c) 2022 Juho Juurinen
 *
 * Licensed under the MIT license: https://opensource.org/licenses/MIT
 */
package fi.jrnn.concisely.processor;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import fi.jrnn.concisely.processor.discovery.ConciselyModelCreator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

import java.util.Set;

/**
 * @author Juho Juurinen
 */
@DisplayName("ConciselyProcessor")
@ExtendWith(MockitoExtension.class)
class TestConciselyProcessor {

    @Spy
    private ConciselyProcessor processor;

    @BeforeEach
    void beforeEach(@Mock ProcessingEnvironment processingEnv, @Mock Messager messager) {
        doReturn(messager).when(processingEnv).getMessager();
        processor.init(processingEnv);
    }

    @DisplayName("When calling 'process'")
    @Nested
    class WhenProcess {

        @Mock
        private RoundEnvironment roundEnv;

        @Mock
        private TypeElement annotation;

        @DisplayName("Given no annotated elements Then should do nothing")
        @Test
        void givenNoAnnotatedElements() {
            doReturn(Set.of()).when(roundEnv).getElementsAnnotatedWith(annotation);
            processor.process(Set.of(annotation), roundEnv);
            verify(processor, never()).modelCreator();
        }

        @DisplayName("Given at least one annotated element Then should create Concisely model")
        @Test
        void givenAnnotatedElements(@Mock ConciselyModelCreator modelCreator, @Mock TypeElement annotatedElement) {
            doReturn(modelCreator).when(processor).modelCreator();
            doReturn(Set.of(annotatedElement)).when(roundEnv).getElementsAnnotatedWith(annotation);
            processor.process(Set.of(annotation), roundEnv);
            verify(modelCreator).createModel(Set.of(annotatedElement));
        }
    }
}
