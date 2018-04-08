package nl.javaan.flattener;

import org.hamcrest.core.Is;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class FlatPathTest {
    @Test
    public void fromName() throws Exception {
        assertThat(FlatPath.fromName("").isEmpty(), Is.is(true));
        assertThat(FlatPath.fromName("lands").isEmpty(), Is.is(false));
    }
}
