package nl.javaan.flattener.exception;

import java.util.function.Consumer;

public class ConsumerWrapper {
    // returns a consumer wrapper that can handle a specific checked exception
    public static <T, E extends Exception> Consumer<T> handlingConsumerWrapper(
            ThrowingConsumer<T, E> throwingConsumer, Class<E> exceptionClass) {

        return i -> {
            try {
                throwingConsumer.accept(i);
            } catch (Exception ex) {
                try {
                    E exCast = exceptionClass.cast(ex);
                    System.err.println(
                            "Exception occurred : " + exCast.getMessage());
                    throw new RuntimeException(exCast.getMessage());
                } catch (ClassCastException ccEx) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }
}
