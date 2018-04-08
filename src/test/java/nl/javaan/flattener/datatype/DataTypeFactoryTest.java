package nl.javaan.flattener.datatype;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DataTypeFactoryTest {
    @Test
    public void fromDataWhenValue() throws Exception {
        DataType result = DataTypeFactory.fromData("abc");
        assertThat(result.getClass().getTypeName(), is(ValueType.class.getTypeName()));
    }

}
