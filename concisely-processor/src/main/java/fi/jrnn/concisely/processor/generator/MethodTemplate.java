/*
 * Copyright (c) 2022 Juho Juurinen
 *
 * Licensed under the MIT license: https://opensource.org/licenses/MIT
 */
package fi.jrnn.concisely.processor.generator;

import static java.lang.String.format;

import javax.lang.model.element.ExecutableElement;

/**
 * @author Juho Juurinen
 */
class MethodTemplate {

    private static final String TEMPLATE = """

                private Stream<%s> %s {
                    return stream.flatMap(t -> safeStream(t.%s));
                }

                public %sConcisely %s() {
                    return new %sConcisely(%s);
                }
            """;

    static String toTarget(String targetTypeName, ExecutableElement accessor) {
        return with(targetTypeName, accessor.toString());
    }

    private static String with(String typeName, String getterName) {
        var targetClassName = upperFirst(typeName);
        var methodName = format("to%sStream()", targetClassName);
        return format(TEMPLATE,
                typeName, methodName, getterName,
                targetClassName, lowerFirst(typeName),
                targetClassName, methodName
        );
    }

    private static String lowerFirst(String s) {
        var firstLetter = s.substring(0, 1).toLowerCase();
        return format("%s%s", firstLetter, s.substring(1));
    }

    private static String upperFirst(String s) {
        var firstLetter = s.substring(0, 1).toUpperCase();
        return format("%s%s", firstLetter, s.substring(1));
    }
}
