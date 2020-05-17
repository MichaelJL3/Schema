package net.ml.schema;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Setter(AccessLevel.PRIVATE)
public final class SchemaImpl<E> implements Schema<E> {
    private Class<E> type;
    private Map<String, Accessor<E, ?>> accessors = new ConcurrentHashMap<>();

    public static <T> SchemaBuilder<T> builder() {
        return new SchemaBuilder<>();
    }

    public static final class SchemaBuilder<E> {
        private final SchemaImpl<E> schema = new SchemaImpl<>();

        public SchemaBuilder<E> type(Class<E> type) {
            schema.type = type;
            return this;
        }

        public SchemaBuilder<E> accessors(Map<String, Accessor<E, ?>> accessors) {
            schema.accessors = accessors;
            return this;
        }

        public SchemaBuilder<E> accessor(Accessor<E, ?> accessor) {
            schema.accessors.put(accessor.getName(), accessor);
            return this;
        }

        public SchemaImpl<E> build() {
            return schema;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Accessor<E, T> getAccessor(final String name) {
        return (Accessor<E, T>) accessors.get(name);
    }

    @Override
    public Accessor<E, ?> getRawAccessor(final String name) {
        return accessors.get(name);
    }

    @Override
    public Collection<Accessor<E, ?>> getAccessors() {
        return accessors.values();
    }

    @Override
    public boolean hasVariable(final String name) {
        return accessors.containsKey(name);
    }

    @Override
    public Set<String> variables() {
        return accessors.keySet();
    }
}
