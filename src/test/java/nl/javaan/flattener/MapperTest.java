package nl.javaan.flattener;

import org.hamcrest.core.Is;
import org.junit.Test;

import java.io.StringWriter;

import static nl.javaan.flattener.DataHelper.*;
import static org.junit.Assert.assertThat;

public class MapperTest {

    public static final Object DEUTSCHLAND = list(
            obj(
                    entry("id", "4"),
                    entry("country", "Deutschland"),
                    entry("lands", list(
                            obj(
                                    entry("id", "40"),
                                    entry("name", "Hessen")
                            ),
                            obj(
                                    entry("id", "41"),
                                    entry("name", "Sachsen")
                            )
                    ))
            ));

    @Test
    public void write() throws Exception {
        Schema schema = Schema.fromData(DEUTSCHLAND);
        Mapper mapper = new Mapper(schema);
        StringWriter writer = new StringWriter();
        mapper.write(writer, DEUTSCHLAND);
        assertThat(writer.toString(), Is.is("id;country\n" +
                "4;Deutschland\n"));
    }

    @Test
    public void writeSub() throws Exception {
        Schema schema = Schema.fromData(DEUTSCHLAND);
        Mapper mapper = new Mapper(schema);
        StringWriter writer = new StringWriter();
        mapper.write(writer, DEUTSCHLAND, "lands");
        assertThat(writer.toString(), Is.is("parentId;id;name\n"
                + "4;40;Hessen\n"
                + "4;41;Sachsen\n"
        ));
    }

    @Test
    public void writeWithoutHeader() throws Exception {
        Schema schema = Schema.fromData(DEUTSCHLAND);
        Mapper mapper = new Mapper(schema);
        StringWriter writer = new StringWriter();
        Options options = Options.defaultOptions()
                .setShowHeader(false);
        mapper.write(writer, DEUTSCHLAND, options);
        assertThat(writer.toString(), Is.is(
                "4;Deutschland\n"));
    }

    @Test
    public void writeWithDifferentSepChar() throws Exception {
        Schema schema = Schema.fromData(DEUTSCHLAND);
        Mapper mapper = new Mapper(schema);
        StringWriter writer = new StringWriter();
        mapper.write(writer, DEUTSCHLAND, Options.defaultOptions().setSepChar(","));
        assertThat(writer.toString(), Is.is("id,country\n" +
                "4,Deutschland\n"));
    }


}
