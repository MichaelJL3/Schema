package net.ml.schema.benchmark;

import net.ml.schema.LambdaFactory;
import net.ml.schema.Setter;
import org.openjdk.jmh.annotations.*;

import java.lang.reflect.Field;

import static net.ml.schema.TestModels.SimpleModel;

public class SetterBenchmark {
    @State(Scope.Thread)
    public static class SetState {
        @Setup(Level.Trial)
        public void setup() throws Exception {
            field = SimpleModel.class.getDeclaredField("message");
            field.setAccessible(true);
        }

        Setter<SimpleModel, String> setter = LambdaFactory.setter(SimpleModel.class, "setMessage", String.class);
        SimpleModel model = new SimpleModel("test");
        Field field;
    }

    @Benchmark
    public boolean directSetterTime(final SetState state) {
        state.model.setMessage("test");
        return true;
    }

    @Benchmark
    public boolean reflectionSetterTime(final SetState state) throws Exception {
        state.field.set(state.model, "test");
        return true;
    }

    @Benchmark
    public boolean lambdaSetterTime(final SetState state) {
        state.setter.set(state.model, "test");
        return true;
    }

    @Benchmark
    public Setter<SimpleModel, String> setterCreationTime() {
        return LambdaFactory.setter(SimpleModel.class, "setMessage", String.class);
    }

    @Benchmark
    public Field reflectiveSetterCreationTime() {
        try {
            Field field = SimpleModel.class.getDeclaredField("message");
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException ex) {
            throw new RuntimeException(ex);
        }
    }
}
