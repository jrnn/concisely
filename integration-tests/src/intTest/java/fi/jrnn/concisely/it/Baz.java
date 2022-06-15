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
public class Baz {

    private String someString;
    private Foo foo;
    private Bar bar;
    private NotInScope notInScope;

    public String getSomeString() {
        return someString;
    }

    public void setSomeString(String someString) {
        this.someString = someString;
    }

    public Foo getFoo() {
        return foo;
    }

    public void setFoo(Foo foo) {
        this.foo = foo;
    }

    public Bar getBar() {
        return bar;
    }

    public void setBar(Bar bar) {
        this.bar = bar;
    }

    public NotInScope getNotInScope() {
        return notInScope;
    }

    public void setNotInScope(NotInScope notInScope) {
        this.notInScope = notInScope;
    }
}
