package net.ml.schema;

import net.ml.schema.TestModels.SimpleModel;
import org.junit.Assert;
import org.junit.Test;

import static net.ml.schema.TestModels.*;

public class SetterTest {
    @Test
    public void setTest() {
        final Setter<SimpleModel, String> setter = LambdaFactory.setter(SimpleModel.class, "setMessage", String.class);
        final SimpleModel model = new SimpleModel();
        final String message = "test";

        Assert.assertNull(model.getMessage());
        setter.set(model, message);
        Assert.assertEquals(message, model.getMessage());
    }

    @Test(expected = UncheckedLambdaFactoryException.class)
    public void setFinalTest() {
        final Setter<FinalModel, String> setter = LambdaFactory.setter(FinalModel.class, "setMessage", String.class);
        final FinalModel model = new FinalModel("test");
        setter.set(model, "test");
    }

    @Test
    public void setterCreationTest() {
        final Setter<SimpleModel, String> setter = LambdaFactory.setter(SimpleModel.class, "setMessage", String.class);
        Assert.assertNotNull(setter);
    }

    @Test(expected = UncheckedLambdaFactoryException.class)
    public void setterCreationInvalidFieldTest() {
        LambdaFactory.setter(SimpleModel.class, "message", String.class);
    }
}
