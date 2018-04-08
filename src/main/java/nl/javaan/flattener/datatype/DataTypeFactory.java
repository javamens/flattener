package nl.javaan.flattener.datatype;

import java.util.Collection;
import java.util.Map;

public class DataTypeFactory {
    public static DataType fromData(Object data) {
        DataType result;
        if (data == null) {
            result = null;
        } else if (data instanceof Collection) {
            result = CollectionType.fromData((Collection) data);
        } else if (data instanceof Map) {
            result = MapType.fromData((Map) data);
        } else {
            result = new ValueType();
        }
        return result;
    }
}
