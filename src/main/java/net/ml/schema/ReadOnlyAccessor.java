package net.ml.schema;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class ReadOnlyAccessor<O, T> implements Accessor<O, T> {
    @NonNull private final String name;
    @NonNull private final Getter<O, T> getter;

    @Override
    public T get(final O obj) {
        return getter.get(obj);
    }

    @Override
    public void set(final O obj, final T val) {
        throw new UnsupportedOperationException();
    }
}
