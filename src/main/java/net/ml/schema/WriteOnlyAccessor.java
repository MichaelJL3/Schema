package net.ml.schema;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class WriteOnlyAccessor<O, T> implements Accessor<O, T> {
    @NonNull private final String name;
    @NonNull private final Setter<O, T> setter;

    @Override
    public T get(final O obj) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void set(final O obj, final T val) {
        setter.set(obj, val);
    }
}
