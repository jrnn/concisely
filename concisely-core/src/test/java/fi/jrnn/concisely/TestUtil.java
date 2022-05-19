package fi.jrnn.concisely;

import java.util.concurrent.ThreadLocalRandom;

public class TestUtil {

    private TestUtil() {
    }

    public static boolean randomBoolean() {
        return ThreadLocalRandom.current().nextBoolean();
    }
}
