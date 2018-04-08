package nl.javaan.flattener;

import java.io.IOException;
import java.io.Writer;

import static nl.javaan.flattener.exception.ConsumerWrapper.handlingConsumerWrapper;

public class Mapper {
    private Schema schema;

    public Mapper(Schema schema) {
        this.schema = schema;
    }

    public void write(Writer writer, Object data, String path, Options options) throws IOException {
        FlattenContext context = FlattenContext.fromName(path);
        if (options.isShowHeader()) {
            writer.write(schema.getHeader(context, options));
            writer.write(System.lineSeparator());
        }
        schema.streamLines(data, context.getPath(), options)
                .forEach(handlingConsumerWrapper(s -> writer.write(s + System.lineSeparator()), IOException.class));
    }

    public void write(Writer writer, Object data, String path) throws IOException {
        write(writer, data, path, Options.defaultOptions());
    }

    public void write(Writer writer, Object data) throws IOException {
        write(writer, data, "", Options.defaultOptions());
    }

    public void write(Writer writer, Object data, Options options) throws IOException {
        write(writer, data, "", options);
    }

}
