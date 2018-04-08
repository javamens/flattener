package nl.javaan.flattener;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static nl.javaan.flattener.DataHelper.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SchemaTest {
    @Test
    public void testWithMap() throws Exception {
        Object data = obj(
                entry("id", "1"),
                entry("country", "Angola")
        );
        Schema schema = Schema.fromData(data);
        assertThat(schema.getHeader(FlattenContext.empty(), Options.defaultOptions()), is("id;country"));
        List<String> result = schema.streamLines(data, FlatPath.empty(), Options.defaultOptions()).collect(Collectors.toList());
        assertThat(result, is(Arrays.asList("1;Angola")));
    }

    @Test
    public void testWithListMap() throws Exception {
        Object data = list(
                obj(
                        entry("id", "2"),
                        entry("country", "Benin")
                ),
                obj(
                        entry("id", "3"),
                        entry("country", "Congo")
                ));
        Schema schema = Schema.fromData(data);
        assertThat(schema.getHeader(FlattenContext.empty(), Options.defaultOptions()), is("id;country"));
        List<String> result = schema.streamLines(data, FlatPath.empty(), Options.defaultOptions()).collect(Collectors.toList());
        assertThat(result, is(Arrays.asList("2;Benin", "3;Congo")));
    }

    @Test
    public void testWithListMapList() throws Exception {
        Object data = list(
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
        Schema schema = Schema.fromData(data);

        assertThat(schema.getHeader(FlattenContext.empty(), Options.defaultOptions()), is("id;country"));
        assertThat(schema.streamLines(data, FlatPath.empty(), Options.defaultOptions()).collect(Collectors.toList()),
                is(Arrays.asList("4;Deutschland")));

        assertThat(schema.getHeader(FlattenContext.fromName("lands"), Options.defaultOptions()), is("parentId;id;name"));
        assertThat(schema.streamLines(data, FlatPath.fromName("lands"), Options.defaultOptions()).collect(Collectors.toList()),
                is(Arrays.asList("4;40;Hessen", "4;41;Sachsen")));

    }

    @Test
    public void testWithListMapListMapList() throws Exception {
        Object data = list(
                obj(
                        entry("id", "5"),
                        entry("country", "France"),
                        entry("regions", list(
                                obj(
                                        entry("id", "50"),
                                        entry("name", "Limousin"),
                                        entry("departments", list(
                                                obj(
                                                        entry("id", "500"),
                                                        entry("name", "Creuse"
                                                        )
                                                ))
                                        )
                                )
                        ))
                ));
        Schema schema = Schema.fromData(data);

        assertThat(schema.getHeader(FlattenContext.empty(), Options.defaultOptions()), is("id;country"));
        assertThat(schema.streamLines(data, FlatPath.empty(), Options.defaultOptions()).collect(Collectors.toList()),
                is(Arrays.asList("5;France")));

        assertThat(schema.getHeader(FlattenContext.fromName("regions"), Options.defaultOptions()), is("parentId;id;name"));
        assertThat(schema.streamLines(data, FlatPath.fromName("regions"), Options.defaultOptions()).collect(Collectors.toList()),
                is(Arrays.asList("5;50;Limousin")));

        assertThat(schema.getHeader(FlattenContext.fromName("regions.departments"), Options.defaultOptions()), is("parentId;id;name"));
        assertThat(schema.streamLines(data, FlatPath.fromName("regions.departments"), Options.defaultOptions()).collect(Collectors.toList()),
                is(Arrays.asList("50;500;Creuse")));

    }

    @Test
    public void testWithMapListValue() throws Exception {
        Object data = obj(
                entry("id", "6"),
                entry("country", "Nepal"),
                entry("mountains", list("Mount Everest", "Lhotse"))
        );
        Schema schema = Schema.fromData(data);

        assertThat(schema.getHeader(FlattenContext.empty(), Options.defaultOptions()), is("id;country"));
        assertThat(schema.streamLines(data, FlatPath.empty(), Options.defaultOptions()).collect(Collectors.toList()), is(Arrays.asList("6;Nepal")));

        assertThat(schema.getHeader(FlattenContext.fromName("mountains"), Options.defaultOptions()), is("parentId;value"));
        assertThat(schema.streamLines(data, FlatPath.fromName("mountains"), Options.defaultOptions()).collect(Collectors.toList()), is(Arrays.asList("6;Mount Everest", "6;Lhotse")));
    }


}
