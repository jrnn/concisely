/*
 * Copyright (c) 2022 Juho Juurinen
 *
 * Licensed under the MIT license: https://opensource.org/licenses/MIT
 */
package fi.jrnn.concisely.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Marks the annotated class as a source for the generation of shorthand convenience classes by Concisely.
 * <p>
 * The annotated class should be a typical POJO (or, in a sense, a JavaBean). Namely, all relationships to other
 * classes annotated with {@link Concisely} should be evident from its properties, and for each of those properties,
 * there should be a corresponding public accessor (typically a "getter").
 *
 * @author Juho Juurinen
 */
@Target(TYPE)
@Retention(SOURCE)
public @interface Concisely {
}
