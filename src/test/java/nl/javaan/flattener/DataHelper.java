package nl.javaan.flattener;

import java.util.*;
import java.util.stream.Collectors;

public class DataHelper {

    public static List<Object> list(Object... values) {
        return Arrays.asList(values);
    }

    public static Map<String, Object> obj(Map.Entry<String, Object>... entries) {
        return Arrays.stream(entries)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new));
    }

    public static Map.Entry<String, Object> entry(String key, Object value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }
}

