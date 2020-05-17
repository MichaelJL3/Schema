package net.ml.schema;

@FunctionalInterface
public interface Setter<O, T> {
    void set(O obj, T val);
}
