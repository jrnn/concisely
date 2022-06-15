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
public class Bar {

    private Integer someBoxedInteger;
    private Foo foo;
    private Baz baz;
    private NotInScope notInScope;

    public Integer getSomeBoxedInteger() {
        return someBoxedInteger;
    }

    public void setSomeBoxedInteger(Integer someBoxedInteger) {
        this.someBoxedInteger = someBoxedInteger;
    }

    public Foo getFoo() {
        return foo;
    }

    public void setFoo(Foo foo) {
        this.foo = foo;
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
