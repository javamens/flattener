package nl.javaan.flattener;

public class FlattenContext {
    private FlatPath originalPath;
    private FlatPath path;
    private int level;

    private FlattenContext() {
    }

    public FlattenContext(FlatPath path) {
        this.path = path;
        this.originalPath = path;
    }

    public FlattenContext deepen() {
        FlattenContext result = new FlattenContext();
        result.originalPath = originalPath;
        if (path.isEmpty()) {
            result.level = level + 1;
        } else {
            result.path = path.stripFirst();
        }
        return result;
    }

    public boolean isTopLevel() {
        return path.isEmpty() && level == 0;
    }

    public String getFirst() {
        return path.getFirst();
    }

    public boolean isEmpty() {
        return path.isEmpty();
    }

    public static FlattenContext empty() {
        return new FlattenContext(FlatPath.empty());
    }

    public static FlattenContext fromName(String name) {
        return new FlattenContext(FlatPath.fromName(name));
    }

    public FlatPath getOriginalPath() {
        return originalPath;
    }

    public FlatPath getPath() {
        return path;
    }
}
