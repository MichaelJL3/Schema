package net.ml.schema.benchmark;

import net.ml.schema.Accessor;
import net.ml.schema.AccessorFactory;
import net.ml.schema.TestModels.SimpleModel;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

public class AccessorBenchmark {
    @State(Scope.Thread)
    public static class AccessorState {
        AccessorFactory<SimpleModel> factory = AccessorFactory.forClass(SimpleModel.class);
        Accessor<SimpleModel, String> accessor = factory.accessor("message", String.class);
        SimpleModel model = new SimpleModel("test");
    }

    @Benchmark
    public String accessorGetterTime(final AccessorState state) {
        return state.accessor.get(state.model);
    }

    @Benchmark
    public boolean accessorSetterTime(final AccessorState state) {
        state.accessor.set(state.model, "test");
        return true;
    }

    @Benchmark
    public Accessor<SimpleModel, String> accessorCreationTime(final AccessorState state) {
        return state.factory.accessor( "message", String.class);
    }

    @Benchmark
    public Accessor<SimpleModel, String> readAccessorCreationTime(final AccessorState state) {
        return state.factory.readOnly( "message", String.class);
    }

    @Benchmark
    public Accessor<SimpleModel, String> writeAccessorCreationTime(final AccessorState state) {
        return state.factory.writeOnly( "message", String.class);
    }
}
