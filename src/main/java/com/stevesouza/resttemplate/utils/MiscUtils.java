package com.stevesouza.resttemplate.utils;



import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MiscUtils {

    private static EnhancedRandom dataGenerator = EnhancedRandomBuilder.aNewEnhancedRandom();

    private static ModelMapper modelMapper = new ModelMapper();
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
        URL url = MiscUtils.class.getClassLoader().getResource(fileName);
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


    public static <T> T convert(Object convertFrom, Class<T> destinationType) {
        return modelMapper.map(convertFrom, destinationType);
    }

    public static String toJsonString(Object pojo) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(pojo);
        } catch (IOException e) {
            throw new RuntimeException("Unable to convert the following object to json: "+pojo, e);
        }
    }

    public static <T> T toObjectFromJsonString(String json, Class<T> destinationType)  {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, destinationType);
        } catch (IOException e) {
            throw new RuntimeException("Unable to convert the json string to the given object type: "+destinationType, e);
        }
    }


     public static <T> T randomData(final Class<T> type, final String... excludedFields) {
        return dataGenerator.nextObject(type, excludedFields);
    }



}
