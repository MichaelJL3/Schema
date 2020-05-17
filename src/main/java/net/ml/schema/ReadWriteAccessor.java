package net.ml.schema;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class ReadWriteAccessor<O, T> implements Accessor<O, T> {
    @NonNull private final String name;
    @NonNull private final Getter<O, T> getter;
    @NonNull private final Setter<O, T> setter;

    @Override
    public T get(final O obj) {
        return getter.get(obj);
    }

    @Override
    public void set(final O obj, final T val) {
        setter.set(obj, val);
    }
}
