/*
 * Copyright (c) 2022 Juho Juurinen
 *
 * Licensed under the MIT license: https://opensource.org/licenses/MIT
 */
package fi.jrnn.concisely;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This is a blatant rip-off from Guava, intended for the exact same purpose as the original (I don't want to add that
 * dependency just for the sake of one semantic annotation).
 * <p>
 * Though Guava explicitly discourages using {@code @VisibleForTesting}, I believe there's no harm in explaining why an
 * implementation detail is given package-private visibility.
 * <p>
 * Sure, having to break encapsulation only to facilitate testing probably means that something stinks. Too bad.
 *
 * @author Juho Juurinen
 */
@Retention(SOURCE)
@Target(METHOD)
public @interface VisibleForTesting {
}
