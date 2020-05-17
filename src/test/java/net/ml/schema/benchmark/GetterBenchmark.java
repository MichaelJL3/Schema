package net.ml.schema.benchmark;

import net.ml.schema.Getter;
import net.ml.schema.LambdaFactory;
import net.ml.schema.TestModels.SimpleModel;
import org.openjdk.jmh.annotations.*;

import java.lang.reflect.Field;

public class GetterBenchmark {
    @State(Scope.Thread)
    public static class GetState {
        @Setup(Level.Trial)
        public void setup() throws Exception {
            field = SimpleModel.class.getDeclaredField("message");
            field.setAccessible(true);
        }

        Getter<SimpleModel, String> getter = LambdaFactory.getter(SimpleModel.class, "getMessage", String.class);
        SimpleModel model = new SimpleModel("test");
        Field field;
    }

    @Benchmark
    public String directGetterTime(final GetState state) {
        return state.model.getMessage();
    }

    @Benchmark
    public String reflectionGetterTime(final GetState state) throws Exception {
        return (String) state.field.get(state.model);
    }

    @Benchmark
    public String lambdaGetterTime(final GetState state) {
        return state.getter.get(state.model);
    }

    @Benchmark
    public Getter<SimpleModel, String> getterCreationTime() {
        return LambdaFactory.getter(SimpleModel.class, "getMessage", String.class);
    }

    @Benchmark
    public Field reflectiveGetterCreationTime() {
        try {
            Field field = SimpleModel.class.getDeclaredField("message");
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException ex) {
            throw new RuntimeException(ex);
        }
    }
}
