package com.stevesouza.resttemplate.utils;



import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyUtils {

    /**
     * Note this method reads a file however it doesn't put newlines in the string.
     * mainly kept it as an example of try-with-resources.
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    @Deprecated
    public static String readFile(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        try (Stream<String> lines = Files.lines(path)) {
            return lines.collect(Collectors.joining()); // converts to a string
        }
    }


    public static URL getResourceURL(String fileName) {
        URL url = MyUtils.class.getClassLoader().getResource(fileName);
        if (null==url) {
            throw new RuntimeException(new FileNotFoundException(fileName));
        }
        return url;
    }

    public static String readFile(URL urlOfFile) throws IOException {
        return IOUtils.toString(urlOfFile, "UTF-8");
    }

    public static String readResourceFile(String fileName) throws IOException {
        return readFile(getResourceURL(fileName));
    }




}
