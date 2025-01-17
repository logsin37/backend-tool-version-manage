package org.logsin37.btvm.util;

public class Assert {

    public static void notNull(Object object, String message, Object... args) {
        if (object == null) {
            throw new RuntimeException(String.format(message, args));
        }
    }

}
