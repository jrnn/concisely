/*
 * Copyright (c) 2022 Juho Juurinen
 *
 * Licensed under the MIT license: https://opensource.org/licenses/MIT
 */
package fi.jrnn.concisely;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Juho Juurinen
 */
public class TestUtil {

    private TestUtil() {
    }

    public static boolean randomBoolean() {
        return ThreadLocalRandom.current().nextBoolean();
    }
}
