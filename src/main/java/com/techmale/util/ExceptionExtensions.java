package com.techmale.util;

import java.lang.reflect.InvocationTargetException;

/**
 * Collection of Utility Exception methods
 * First method is
 * Exception.when(condition,message[,string,format,arguments])
 */
public final class ExceptionExtensions {

    public static final ExceptionExtensions IllegalArgumentException = new ExceptionExtensions(IllegalArgumentException.class);
    public static final ExceptionExtensions NullPointerException = new ExceptionExtensions(NullPointerException.class);

    private final Class clazz;

    /**
     * Instantiate Exception Extension
     *
     * @param clazz Class of RuntimeException type to throw
     */
    private ExceptionExtensions(Class clazz) {
        this.clazz = clazz;
    }

    /**
     * @param condition If true, then exception is thrown
     * @param msg       Msg to display (uses String.format)
     * @param args      optional varArgs for string format
     */
    public void when(boolean condition, String msg, Object... args) {
        if (condition) {
            try {
                String formattedMessage = String.format(msg, args);
                throw (RuntimeException) clazz.getConstructor(String.class).newInstance(formattedMessage);
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
}