package nl.javaan.flattener.datatype;

import nl.javaan.flattener.FlattenContext;
import nl.javaan.flattener.Options;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ValueType implements DataType {

    @Override
    public Stream<String> streamLines(Object data, FlattenContext context, Object parentRef, Options options) {

        return Stream.of(parentRef.toString() + options.getSepChar() + data.toString());
    }

    @Override
    public Stream<String> values(Object data) {
        return data == null ? Stream.empty() : Stream.of(data.toString());
    }

    @Override
    public Stream<String> getCollectionNames(String prefix) {
        return Stream.empty();
    }

    @Override
    public String getHeader(FlattenContext context, Options options) {
        if (context.isEmpty()) {

            Stream<String> headerNames;
            if (context.isTopLevel() && !context.getOriginalPath().isEmpty()) {
                headerNames = Stream.of(options.getParentIdHeader(), options.getValueHeader());
            } else {
                headerNames = Stream.of(options.getValueHeader());
            }
            return headerNames
                    .collect(Collectors.joining(options.getSepChar()));
        } else {
            return "";
        }
    }

}
