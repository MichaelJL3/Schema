package net.ml.schema;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "forClass")
public final class AccessorFactory<O> {
    private final Class<O> meta;

    public <T> Setter<O, T> setter(final String name, final Class<T> type) {
        return LambdaFactory.setter(meta, "set" + capitalize(name), type);
    }

    public <T> Getter<O, T> getter(final String name, final Class<T> type) {
        return LambdaFactory.getter(meta, "get" + capitalize(name), type);
    }

    public <T> Accessor<O, T> accessor(final String name, final Class<T> type) {
        return new ReadWriteAccessor<>(name, getter(name, type), setter(name, type));
    }

    public <T> Accessor<O, T> readOnly(final String name, final Class<T> type) {
        return new ReadOnlyAccessor<>(name, getter(name, type));
    }

    public <T> Accessor<O, T> writeOnly(final String name, final Class<T> type) {
        return new WriteOnlyAccessor<>(name, setter(name, type));
    }

    private static String capitalize(final String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
