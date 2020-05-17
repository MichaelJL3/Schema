package net.ml.function;

import java.util.function.BiFunction;
import java.util.function.Function;

@FunctionalInterface
public interface BiPartialFunction<A, B, R> extends BiFunction<A, B, R> {
    default Function<B, R> partial(final A a) {
        return b -> apply(a, b);
    }
}
