package nl.javaan.flattener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FlatPath {
    private List<FlatField> fields = new ArrayList<>();

    public static FlatPath fromName(String name) {
        String[] split = name.split("\\.");
        List<FlatField> fields = Arrays.stream(split)
                .filter(s -> !s.isEmpty())
                .map(FlatField::new)
                .collect(Collectors.toList());
        return new FlatPath(fields);
    }

    private FlatPath() {
    }

    public FlatPath(List<FlatField> fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return fields.stream()
                .map(FlatField::getName)
                .collect(Collectors.joining("."));
    }

    public static FlatPath empty() {
        return new FlatPath();
    }

    public String getFirst() {
        return fields.get(0).getName();
    }

    public boolean isEmpty() {
        return fields.isEmpty();
    }

    public FlatPath stripFirst() {
        return new FlatPath(fields.subList(1, fields.size()));
    }
}
