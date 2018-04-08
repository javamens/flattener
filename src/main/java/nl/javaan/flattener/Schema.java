package nl.javaan.flattener;

import nl.javaan.flattener.datatype.DataType;
import nl.javaan.flattener.datatype.DataTypeFactory;

import java.util.stream.Stream;

public class Schema {

    private DataType dataType;

    public static Schema fromData(Object data) {
        Schema result = new Schema();
        result.dataType = DataTypeFactory.fromData(data);
        return result;
    }

    public Stream<String> streamLines(Object data, FlatPath path, Options options) {
        return dataType.streamLines(data, new FlattenContext(path), null, options);
    }

    public String getHeader(FlattenContext context, Options options) {
        return dataType.getHeader(context, options);
    }
}
