package net.ml.schema.benchmark;

import net.ml.schema.*;
import net.ml.schema.TestModels.SimpleModel;
import org.openjdk.jmh.annotations.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class SchemaBenchmark {
    @State(Scope.Thread)
    public static class SchemaState {
        @Setup(Level.Trial)
        public void setup() throws Exception {
            field = SimpleModel.class.getDeclaredField("message");
            field.setAccessible(true);

            reflectiveMapping = new HashMap<>();
            reflectiveMapping.put("message", field);
        }

        SimpleModel model = new SimpleModel("test");
        Schema<SimpleModel> schema = SchemaFactory.getSchema(SimpleModel.class);
        Field field;
        Map<String, Field> reflectiveMapping;
    }

    @Benchmark
    public Schema<SimpleModel> schemaCreationTime() {
        return SchemaFactory.getSchema(SimpleModel.class);
    }

    @Benchmark
    public Schema<SimpleModel> readOnlySchemaCreationTime() {
        return SchemaFactory.getSchema(SimpleModel.class, Access.READ);
    }

    @Benchmark
    public Schema<SimpleModel> writeOnlySchemaCreationTime() {
        return SchemaFactory.getSchema(SimpleModel.class, Access.WRITE);
    }

    @Benchmark
    public String getterFromReflectiveMapTime(final SchemaState state) throws Exception {
        return (String) state.reflectiveMapping.get("message").get(state.model);
    }

    @Benchmark
    public boolean setterFromReflectiveMapTime(final SchemaState state) throws Exception {
        state.reflectiveMapping.get("message").set(state.model, "test");
        return true;
    }

    @Benchmark
    public String schemaGetterTime(final SchemaState state) {
        return state.schema.<String>getAccessor("message").get(state.model);
    }

    @Benchmark
    public boolean schemaSetterTime(final SchemaState state) {
        state.schema.getAccessor("message").set(state.model, "test");
        return true;
    }

    @Benchmark
    public Accessor<SimpleModel, String> schemaAccessorTime(final SchemaState state) {
        return state.schema.getAccessor("message");
    }

    @Benchmark
    public Accessor<SimpleModel, ?> schemaRawAccessorTime(final SchemaState state) {
        return state.schema.getRawAccessor("message");
    }
}
