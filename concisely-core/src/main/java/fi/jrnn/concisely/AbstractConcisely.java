/*
 * Copyright (c) 2022 Juho Juurinen
 *
 * Licensed under the MIT license: https://opensource.org/licenses/MIT
 */
package fi.jrnn.concisely;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Juho Juurinen
 */
abstract class AbstractConcisely<T> extends SafeWrappedStream<T> {

    AbstractConcisely(Stream<T> stream) {
        super(stream);
    }

    AbstractConcisely(T t) {
        super(t);
    }

    AbstractConcisely(Collection<T> ts) {
        super(ts);
    }

    AbstractConcisely(Optional<T> maybeT) {
        super(maybeT);
    }
}
