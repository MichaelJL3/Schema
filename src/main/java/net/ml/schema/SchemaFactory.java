package net.ml.schema;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SchemaFactory {
    public static <E> Schema<E> getSchema(final Class<E> type) {
        return getSchema(type, Access.READ_WRITE);
    }

    public static <E> Schema<E> getSchema(final Class<E> type, final Access access) {
        final Function<Field, Accessor<E, ?>> accessorFunction = router(AccessorFactory.forClass(type), access);

        final Map<String, Accessor<E, ?>> accessors = Stream.of(type.getDeclaredFields()).parallel()
            .filter(f -> !Modifier.isTransient(f.getModifiers()))
            .map(accessorFunction)
            .collect(Collectors.toMap(Accessor::getName, Function.identity()));

        return SchemaImpl.<E>builder()
            .type(type)
            .accessors(accessors)
            .build();
    }

    private static <E> Function<Field, Accessor<E, ?>> router(final AccessorFactory<E> accessorFactory, final Access access) {
        switch (access) {
            case READ:
                return field -> accessorFactory.readOnly(field.getName(), field.getType());
            case WRITE:
                return field -> Modifier.isFinal(field.getModifiers()) ?
                    accessorFactory.readOnly(field.getName(), field.getType()) :
                    accessorFactory.writeOnly(field.getName(), field.getType());
            default:
                return field -> Modifier.isFinal(field.getModifiers()) ?
                    accessorFactory.readOnly(field.getName(), field.getType()) :
                    accessorFactory.accessor(field.getName(), field.getType());
        }
    }
}
