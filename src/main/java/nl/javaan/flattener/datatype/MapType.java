package nl.javaan.flattener.datatype;

import nl.javaan.flattener.FlattenContext;
import nl.javaan.flattener.Options;
import nl.javaan.flattener.UnexpectedDataException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapType implements DataType {

    private Map<String, DataType> members = new LinkedHashMap<>();

    public static MapType fromData(Map<String, ?> data) {

        MapType result = new MapType();
        for (Map.Entry<String, ?> entry : data.entrySet()) {
            DataType dataType = DataTypeFactory.fromData(entry.getValue());
            if (dataType != null) {
                result.members.put(entry.getKey(), dataType);
            }
        }
        return result;
    }

    @Override
    public Stream<String> streamLines(Object data, FlattenContext context, Object parentRef, Options options) {
        Map<?, ?> map = dataToMap(data);
        if (context.isTopLevel()) {
            Stream<String> parent = parentRef == null ? Stream.empty() : Stream.of(parentRef.toString());

            return Stream.of(
                    Stream.concat(parent, values(data))
                            .collect(Collectors.joining(options.getSepChar())));
        }
        Object value = map.get(context.getFirst());
        if (value == null) {
            return Stream.empty();
        } else {
            return members.get(context.getFirst()).streamLines(value, context.deepen(), map.get("id"), options);
        }
    }

    @Override
    public Stream<String> values(Object data) {
        Map<?, ?> map = dataToMap(data);
        return members.entrySet().stream()
                .filter(e -> !(e.getValue() instanceof CollectionType))
                .flatMap(e -> e.getValue().values(map.get(e.getKey())));
    }

    @Override
    public Stream<String> getCollectionNames(String prefix) {
        return members.entrySet().stream()
                .flatMap(e -> e.getValue().getCollectionNames(e.getKey()))
                .map(n -> (prefix.length() > 0 ? prefix + "." : "") + n);
    }

    @Override
    public String getHeader(FlattenContext context, Options options) {
        if (context.isEmpty()) {

            final Stream<String> normalHeaders = members.entrySet().stream()
                    .filter(e -> !(e.getValue() instanceof CollectionType))
                    .map(Map.Entry::getKey);
            Stream<String> headerNames;
            if (context.isTopLevel() && !context.getOriginalPath().isEmpty()) {
                headerNames = Stream.concat(Stream.of(options.getParentIdHeader()), normalHeaders);
            } else {
                headerNames = normalHeaders;
            }
            return headerNames
                    .collect(Collectors.joining(options.getSepChar()));
        } else {
            return members.get(context.getFirst()).getHeader(context.deepen(), options);
        }
    }

    private Map dataToMap(Object data) {
        try {
            return (Map) data;
        } catch (ClassCastException e) {
            throw new UnexpectedDataException("not a map");
        }
    }


}
