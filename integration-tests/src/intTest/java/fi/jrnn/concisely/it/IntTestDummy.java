package fi.jrnn.concisely.it;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// TODO this doesn't do anything ... just ensuring that integration tests can be run, and that the annotation processor
//      is fired during compilation

@DisplayName("I'M A DUMMY, DON'T MIND ME")
class IntTestDummy {

    @Test
    void theOwlsAreNotWhatTheySeem() {
        assertThat(SomeClass.SOME_VALUE, is(1));
    }
}
