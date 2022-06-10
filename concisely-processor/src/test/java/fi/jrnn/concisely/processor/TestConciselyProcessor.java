package fi.jrnn.concisely.processor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import java.util.Set;

@DisplayName("ConciselyProcessor")
@ExtendWith(MockitoExtension.class)
class TestConciselyProcessor {

    @InjectMocks
    private ConciselyProcessor processor;

    @DisplayName("When calling 'process' Then nothing really happens... yet")
    @Test
    void whenProcess(@Mock Set<? extends TypeElement> annotations, @Mock RoundEnvironment roundEnv) {
        // TODO just a useless placeholder test, throw out at some point
        assertThat(processor.process(annotations, roundEnv), is(false));
    }
}
