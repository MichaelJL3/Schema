package net.ml.schema;

import org.junit.Assert;
import org.junit.Test;

import static net.ml.schema.TestModels.*;

public class GetterTest {
    @Test
    public void getTest() {
        final Getter<SimpleModel, String> getter = LambdaFactory.getter(SimpleModel.class, "getMessage", String.class);
        final String message = "test";
        final SimpleModel model = new SimpleModel(message);
        Assert.assertEquals(message, getter.get(model));
    }

    @Test
    public void getFinalTest() {
        final Getter<FinalModel, String> getter = LambdaFactory.getter(FinalModel.class, "getMessage", String.class);
        final String message = "test";
        final FinalModel model = new FinalModel(message);
        Assert.assertEquals(message, getter.get(model));
    }

    @Test
    public void getterCreationTest() {
        final Getter<SimpleModel, String> getter = LambdaFactory.getter(SimpleModel.class, "getMessage", String.class);
        Assert.assertNotNull(getter);
    }

    @Test(expected = UncheckedLambdaFactoryException.class)
    public void getterCreationInvalidFieldTest() {
        LambdaFactory.getter(SimpleModel.class, "message", String.class);
    }
}
