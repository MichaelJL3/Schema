package net.ml.schema;

import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

import static net.ml.schema.TestModels.*;

public class SchemaTest {
    @Test
    public void schemaCreationTest() {
        final Schema<SimpleModel> schema = SchemaFactory.getSchema(SimpleModel.class);
        Assert.assertNotNull(schema);
        Assert.assertTrue(schema.hasVariable("message"));
    }

    @Test
    public void schemaWithTransientCreationTest() {
        final Schema<TransientModel> schema = SchemaFactory.getSchema(TransientModel.class);
        Assert.assertNotNull(schema);
        Assert.assertFalse(schema.hasVariable("message"));
    }

    @Test
    public void schemaWithFinalCreationTest() {
        final Schema<FinalModel> schema = SchemaFactory.getSchema(FinalModel.class);
        Assert.assertNotNull(schema);
        Assert.assertTrue(schema.hasVariable("message"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void schemaWithFinalWriteTest() {
        final Schema<FinalModel> schema = SchemaFactory.getSchema(FinalModel.class);
        schema.getAccessor("message").set(new FinalModel("test"), "test");
    }

    @Test
    public void readOnlySchemaTest() {
        final Schema<SimpleModel> schema = SchemaFactory.getSchema(SimpleModel.class, Access.READ);
        final String message = "test";
        final SimpleModel model = new SimpleModel(message);
        Assert.assertEquals(message, schema.getAccessor("message").get(model));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void writeToReadOnlySchemaTest() {
        final Schema<SimpleModel> schema = SchemaFactory.getSchema(SimpleModel.class, Access.READ);
        final String message = "test";
        final SimpleModel model = new SimpleModel();
        schema.getAccessor("message").set(model, message);
    }

    @Test
    public void writeOnlySchemaTest() {
        final Schema<SimpleModel> schema = SchemaFactory.getSchema(SimpleModel.class, Access.WRITE);
        final String message = "test";
        final SimpleModel model = new SimpleModel();
        schema.getAccessor("message").set(model, message);
        Assert.assertEquals(message, model.getMessage());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void readFromWriteOnlySchemaTest() {
        final Schema<SimpleModel> schema = SchemaFactory.getSchema(SimpleModel.class, Access.WRITE);
        final String message = "test";
        final SimpleModel model = new SimpleModel(message);
        schema.getAccessor("message").get(model);
    }

    @Test
    public void schemaTest() {
        final Schema<SimpleModel> schema = SchemaFactory.getSchema(SimpleModel.class);
        final String message = "test";
        final SimpleModel model = new SimpleModel();
        final Accessor<SimpleModel, String> accessor = schema.getAccessor("message");
        accessor.set(model, message);
        Assert.assertEquals(message, model.getMessage());
        Assert.assertEquals(message, accessor.get(model));
    }

    @Test
    public void variableTest() {
        final Schema<SimpleModel> schema = SchemaFactory.getSchema(SimpleModel.class, Access.READ);
        final Set<String> variables = schema.variables();
        Assert.assertFalse(variables.isEmpty());
        Assert.assertTrue(variables.contains("message"));
    }

    @Test
    public void rawAccessorTest() {
        final Schema<SimpleModel> schema = SchemaFactory.getSchema(SimpleModel.class, Access.READ);
        final Accessor<SimpleModel, ?> accessor = schema.getRawAccessor("message");
        Assert.assertNotNull(accessor);
    }
}
