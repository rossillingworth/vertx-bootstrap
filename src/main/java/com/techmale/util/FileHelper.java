package com.techmale.util;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import static com.techmale.util.ExceptionExtensions.*;

/**
 * Collection of utility methods for manipulating files stored in Resources.
 */
public final class FileHelper {

    private FileHelper() {
    }

    /**
     * Reads a file and returns it as a string.
     *
     * @param path The path to the file relative to the Resources directory.
     * @return String representation of a file.
     * @throws java.io.IOException              Thrown if the specified file is not found.
     * @throws IllegalArgumentException Thrown if path given is not a file.
     */
    public static String readFileAsString(String path) throws IOException {
        StringWriter writer = new StringWriter();
        IOUtils.copy(readFile(path), writer, "UTF-8");
        return writer.toString();
    }

    /**
     * Reads a file and returns it as an InputStream
     *
     * @param path The path to the file relative to the Resources directory.
     * @return InputStream representation of a file.
     * @throws IllegalArgumentException Thrown if path given is not a file.
     */
    public static InputStream readFileAsInputStream(String path) {
        return readFile(path);
    }

    /**
     * @param path Path to file.
     * @return Input stream representation of file.
     * @throws IllegalArgumentException Thrown if path given is not a file.
     */
    private static InputStream readFile(String path) {
        // Path requires a leading /
        // add if not present.
        String newPath = path;

        if (!path.startsWith("/")) {
            newPath = "/" + path;
        }

        InputStream fileStream = FileHelper.class.getResourceAsStream(newPath);
        IllegalArgumentException.when(fileStream == null, "Path specified does not point to a file, path: %s", path);
        return fileStream;
    }
}