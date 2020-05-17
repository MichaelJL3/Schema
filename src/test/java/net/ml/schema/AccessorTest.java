package net.ml.schema;

import net.ml.schema.TestModels.SimpleModel;
import org.junit.Assert;
import org.junit.Test;

public class AccessorTest {
    private static final AccessorFactory<SimpleModel> ACCESSOR_FACTORY = AccessorFactory.forClass(SimpleModel.class);

    @Test
    public void accessorCreationTest() {
        final Accessor<SimpleModel, String> accessor = ACCESSOR_FACTORY.accessor("message", String.class);
        Assert.assertNotNull(accessor);
        Assert.assertEquals("message", accessor.getName());
    }

    @Test
    public void readOnlyAccessorTest() {
        final Accessor<SimpleModel, String> accessor = ACCESSOR_FACTORY.readOnly("message", String.class);
        final String message = "test";
        final SimpleModel model = new SimpleModel(message);
        Assert.assertEquals(message, accessor.get(model));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void writeToReadOnlyAccessorTest() {
        final Accessor<SimpleModel, String> accessor = ACCESSOR_FACTORY.readOnly("message", String.class);
        final SimpleModel model = new SimpleModel();
        accessor.set(model, "test");
    }

    @Test
    public void writeOnlyAccessorTest() {
        final Accessor<SimpleModel, String> accessor = ACCESSOR_FACTORY.writeOnly("message", String.class);
        final String message = "test";
        final SimpleModel model = new SimpleModel();
        accessor.set(model, message);
        Assert.assertEquals(message, model.getMessage());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void readFromWriteOnlyAccessorTest() {
        final Accessor<SimpleModel, String> accessor = ACCESSOR_FACTORY.writeOnly("message", String.class);
        final SimpleModel model = new SimpleModel();
        accessor.get(model);
    }

    @Test
    public void readWriteAccessorTest() {
        final Accessor<SimpleModel, String> accessor = ACCESSOR_FACTORY.accessor("message", String.class);
        final String message = "test";
        final SimpleModel model = new SimpleModel();
        accessor.set(model, message);
        Assert.assertEquals(message, model.getMessage());
        Assert.assertEquals(message, accessor.get(model));
    }
}
