/*
 * Copyright (c) 2022 Juho Juurinen
 *
 * Licensed under the MIT license: https://opensource.org/licenses/MIT
 */
package fi.jrnn.concisely.it;

import fi.jrnn.concisely.annotation.Concisely;

/**
 * @author Juho Juurinen
 */
@Concisely
public class Foo {

    private int somePrimitiveInteger;
    private Bar bar;
    private Baz baz;
    private NotInScope notInScope;

    public int getSomePrimitiveInteger() {
        return somePrimitiveInteger;
    }

    public void setSomePrimitiveInteger(int somePrimitiveInteger) {
        this.somePrimitiveInteger = somePrimitiveInteger;
    }

    public Bar getBar() {
        return bar;
    }

    public void setBar(Bar bar) {
        this.bar = bar;
    }

    public Baz getBaz() {
        return baz;
    }

    public void setBaz(Baz baz) {
        this.baz = baz;
    }

    public NotInScope getNotInScope() {
        return notInScope;
    }

    public void setNotInScope(NotInScope notInScope) {
        this.notInScope = notInScope;
    }
}
