package net.ml.schema;

@FunctionalInterface
public interface Getter<O, T> {
    T get(O obj);
}
