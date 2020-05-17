package net.ml.schema;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.invoke.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LambdaFactory {
    private static final MethodType GETTER_TYPE = MethodType.methodType(Getter.class);
    private static final MethodType SETTER_TYPE = MethodType.methodType(Setter.class);
    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();

    @SuppressWarnings("unchecked")
    public static <O, T> Getter<O, T> getter(final Class<O> meta, final String name, final Class<T> type) {
        try {
            final MethodHandle target = LOOKUP.findVirtual(meta, name, MethodType.methodType(type));
            final MethodType func = target.type();

            final CallSite site = LambdaMetafactory.metafactory(LOOKUP,
                "get",
                GETTER_TYPE,
                func.erase(),
                target,
                func
            );

            return (Getter<O, T>) site.getTarget().invokeExact();
        } catch (final Throwable ex) {
            throw new UncheckedLambdaFactoryException(ex);
        }
    }

    @SuppressWarnings("unchecked")
    public static <O, T> Setter<O, T> setter(final Class<O> meta, final String name, final Class<T> type) {
        try {
            final MethodHandle target = LOOKUP.findVirtual(meta, name, MethodType.methodType(void.class, type));
            final MethodType func = target.type();

            final CallSite site = LambdaMetafactory.metafactory(LOOKUP,
                "set",
                SETTER_TYPE,
                func.erase(),
                target,
                func
            );

            return (Setter<O, T>) site.getTarget().invoke();
        } catch (final Throwable ex) {
            throw new UncheckedLambdaFactoryException(ex);
        }
    }
}
