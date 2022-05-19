# concisely

Assume you have a large Java domain where the POJOs are garbage and NullPointerExceptions are
lurking around every corner. You'll likely have to deal with something like this:
```java
Waldo findSpecificKindOfWaldo(Foo foo) {
    if (foo != null && foo.getBars() != null) {
        for (Bar bar : foo.getBars()) {
            if (bar.getBaz() == Baz.QUX && bar.getQuuxes() != null) {
                for (Quux quux : bar.getQuuxes()) {
                    if (quux.getCorge() == Corge.GRAULT
                            && quux.getGarply() != null
                            && quux.getGarply().getWaldos() != null) {
                        for (Waldo waldo : quux.getGarply().getWaldos()) {
                            if (waldo.getFred() == Fred.PLUGH) {
                                return waldo;
                            }
                        }
                    }
                }
            }
        }
    }
    return null;
}
```
Or, maybe more modernly:
```java
Optional<Waldo> findSpecificKindOfWaldo(Foo foo) {
    return Stream.ofNullable(foo)
            .flatMap(_foo -> Stream.ofNullable(_foo.getBars()))
            .flatMap(Collection::stream)
            .filter(bar -> bar.getBaz().isQux())
            .flatMap(bar -> Stream.ofNullable(bar.getQuuxes()))
            .flatMap(Collection::stream)
            .filter(quux -> quux.getCorge().isGrault())
            .flatMap(quux -> Stream.ofNullable(quux.getGarply()))
            .flatMap(garply -> Stream.ofNullable(garply.getWaldos()))
            .flatMap(Collection::stream)
            .filter(waldo -> waldo.getFred().isFred())
            .findAny();
}
```
Wouldn't it be nice if you could, instead, write the same thing more **concisely** (ahem) and
without the NPE paranoia?
```java
Optional<Waldo> findSpecificKindOfWaldo(Foo foo) {
    return concisely(foo)
            .bar(bar -> bar.getBaz().isQux())
            .quux(quux -> quux.getCorge().isGrault())
            .garply()
            .waldo(waldo -> waldo.getFred().isFred())
            .any();
}
```
The ambition with **concisely** is to generate a layer of abstraction over your domain for easier
access, preferably using only one annotation. So, you would decorate POJOs with `@Concisely`,
compile your project, and then you'd have the `concisely`-shorthand (as sketched above) at your
disposal.

This probably will never happen, though, because I don't have a clue about annotation processing,
am not nearly smart enough to carry out anything of this caliber, and am swamped with work and
family demands.
