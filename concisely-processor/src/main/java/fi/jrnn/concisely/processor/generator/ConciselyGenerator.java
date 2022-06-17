/*
 * Copyright (c) 2022 Juho Juurinen
 *
 * Licensed under the MIT license: https://opensource.org/licenses/MIT
 */
package fi.jrnn.concisely.processor.generator;

import fi.jrnn.concisely.processor.model.ConciselyAnnotatedClass;

import javax.annotation.processing.Filer;

import java.io.IOException;
import java.util.Set;

/**
 * @author Juho Juurinen
 */
public class ConciselyGenerator {

    private final Filer filer;

    public ConciselyGenerator(Filer filer) {
        this.filer = filer;
    }

    public void generateSourceFiles(Set<ConciselyAnnotatedClass> conciselyClasses) {
        var classWriter = new ClassWriter(filer);
        for (var c : conciselyClasses) {
            try {
                classWriter.generate(c);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
