package net.ml.schema;

import java.util.Collection;
import java.util.Set;

public interface Schema<E> {
    <T> Accessor<E, T> getAccessor(String name);
    Accessor<E, ?> getRawAccessor(String name);
    Collection<Accessor<E, ?>> getAccessors();
    boolean hasVariable(String name);
    Set<String> variables();
}
