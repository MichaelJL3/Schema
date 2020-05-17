package net.ml.schema;

public interface Accessor<O, T> extends Setter<O, T>, Getter<O, T> {
    String getName();
}
