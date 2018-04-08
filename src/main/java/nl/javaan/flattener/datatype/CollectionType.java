package nl.javaan.flattener.datatype;

import nl.javaan.flattener.FlattenContext;
import nl.javaan.flattener.Options;
import nl.javaan.flattener.UnexpectedDataException;

import java.util.Collection;
import java.util.stream.Stream;

public class CollectionType implements DataType {
    private DataType dataType;

    public static CollectionType fromData(Collection<?> data) {
        CollectionType result = new CollectionType();
        for (Object value : data) {
            result.dataType = DataTypeFactory.fromData(value);
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Stream<String> streamLines(Object data, FlattenContext context, Object parentRef, Options options) {
        return dataToCollection(data).stream()
                .flatMap(e -> dataType.streamLines(e, context, parentRef, options));
    }

    @Override
    public Stream<String> values(Object data) {
        return null;
    }

    @Override
    public Stream<String> getCollectionNames(String prefix) {
        return Stream.concat(
                Stream.of(prefix),
                dataType.getCollectionNames(prefix));
    }

    @Override
    public String getHeader(FlattenContext context, Options options) {
        String result = "";
        result += dataType.getHeader(context, options);
        return result;
    }

    private Collection dataToCollection(Object data) {
        try {
            return (Collection) data;
        } catch (ClassCastException e) {
            throw new UnexpectedDataException("not a collection");
        }
    }
}
