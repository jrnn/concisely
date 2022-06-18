/*
 * Copyright (c) 2022 Juho Juurinen
 *
 * Licensed under the MIT license: https://opensource.org/licenses/MIT
 */
package fi.jrnn.concisely.processor.generator;

import static java.lang.String.format;

/**
 * @author Juho Juurinen
 */
class ConstructorsTemplate {

    private static final String GENERIC_TYPE = "T";
    private static final String DEFAULT_VISIBILITY = "";
    private static final String PUBLIC_VISIBILITY = "public ";
    private static final String TEMPLATE = """

                %s%s(Stream<%s> stream) {
                    super(stream);
                }

                %s%s(%s t) {
                    super(t);
                }

                %s%s(Collection<%s> ts) {
                    super(ts);
                }

                %s%s(Optional<%s> maybeT) {
                    super(maybeT);
                }
            """;

    static String forAbstractClass(String className) {
        return with(DEFAULT_VISIBILITY, className, GENERIC_TYPE);
    }

    static String forPublicClass(String className, String typeName) {
        return with(PUBLIC_VISIBILITY, className, typeName);
    }

    private static String with(String visibility, String className, String typeName) {
        return format(TEMPLATE,
                visibility, className, typeName,
                visibility, className, typeName,
                visibility, className, typeName,
                visibility, className, typeName
        );
    }
}
