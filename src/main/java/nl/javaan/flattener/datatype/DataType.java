package nl.javaan.flattener.datatype;

import nl.javaan.flattener.FlattenContext;
import nl.javaan.flattener.Options;

import java.util.stream.Stream;

public interface DataType {

    Stream<String> streamLines(Object data, FlattenContext context, Object parentRef, Options options);

    Stream<String> values(Object data);

    Stream<String> getCollectionNames(String prefix);

    String getHeader(FlattenContext context, Options options);
}

