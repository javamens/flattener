package nl.javaan.flattener;

public class Options {
    private boolean showHeader = true;
    private String sepChar = ";";
    private String parentIdHeader = "parentId";
    private String valueHeader = "value";

    private Options() {

    }

    public static Options defaultOptions() {
        return new Options();
    }

    public boolean isShowHeader() {
        return showHeader;
    }

    public Options setShowHeader(boolean showHeader) {
        this.showHeader = showHeader;
        return this;
    }

    public String getSepChar() {
        return sepChar;
    }

    public Options setSepChar(String sepChar) {
        this.sepChar = sepChar;
        return this;
    }

    public String getValueHeader() {
        return valueHeader;
    }

    public Options setValueHeader(String valueHeader) {
        this.valueHeader = valueHeader;
        return this;
    }

    public String getParentIdHeader() {
        return parentIdHeader;
    }

    public Options setParentIdHeader(String parentIdHeader) {
        this.parentIdHeader = parentIdHeader;
        return this;
    }
}
