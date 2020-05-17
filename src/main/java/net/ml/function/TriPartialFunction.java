package net.ml.function;

@FunctionalInterface
public interface TriPartialFunction<A, B, C, R> extends TriFunction<A, B, C, R> {
    default BiPartialFunction<B, C, R> partial(final A a) {
        return (b, c) -> apply(a, b, c);
    }
}
