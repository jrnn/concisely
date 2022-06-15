/*
 * Copyright (c) 2022 Juho Juurinen
 *
 * Licensed under the MIT license: https://opensource.org/licenses/MIT
 */
package fi.jrnn.concisely;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

/**
 * @author Juho Juurinen
 */
public class TestUtil {

    private TestUtil() {
    }

    @SafeVarargs
    public static <E extends Enum<E>> E randomEnumExcluding(Class<E> enumClass, E... toExclude) {
        var exclusionSet = Set.of(toExclude);
        var constants = Stream
                .of(enumClass.getEnumConstants())
                .filter(constant -> !exclusionSet.contains(constant))
                .toList();

        return constants.get(ThreadLocalRandom.current().nextInt(constants.size()));
    }
}
