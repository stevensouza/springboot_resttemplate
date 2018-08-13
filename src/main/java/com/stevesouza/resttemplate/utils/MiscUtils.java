package com.stevesouza.resttemplate.utils;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MiscUtils {

    private static EnhancedRandom dataGenerator = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().build();
    private static ModelMapper modelMapper = new ModelMapper();
    private static int DEFAULT_RANDOM_COLLECTION_SIZE = 10;


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

    public static <T> T convert(Object convertFrom, TypeToken<T> typeToken) {
        return modelMapper.map(convertFrom, typeToken.getType());
    }

    public static String toJsonString(Object pojo) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(pojo);
        } catch (IOException e) {
            throw new RuntimeException("Unable to convert the following object to json: "+pojo, e);
        }
    }

    public static String toJsonStringFromJsonNode(JsonNode jsonNode) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(jsonNode);
        } catch (IOException e) {
            throw new RuntimeException("Unable to convert the following JsonNode to a json string: "+jsonNode, e);
        }
    }

    public static JsonNode toJsonNode(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readTree(json);
        } catch (IOException e) {
            throw new RuntimeException("Unable to convert the json string to a JsonNode", e);
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

    public static <T>  List<T> randomData(List<T> list, final Class<T> type, final String... excludedFields) {
        for (int i=0; i<DEFAULT_RANDOM_COLLECTION_SIZE; i++) {
            list.add(dataGenerator.nextObject(type, excludedFields));
        }

        return list;
    }

    public static <T> Set<T> randomData(Set<T> set, final Class<T> type, final String... excludedFields) {
        for (int i=0; i<DEFAULT_RANDOM_COLLECTION_SIZE; i++) {
            set.add(dataGenerator.nextObject(type, excludedFields));
        }

        return set;
    }

    public static <T> T randomDataPopulateCollections(final Class<T> type, final String... excludedFields) {
        EnhancedRandom randomGenerator = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().overrideDefaultInitialization(true).build();
        return randomGenerator.nextObject(type, excludedFields);
    }

    public static <T>  List<T> randomDataPopulateCollections(List<T> list, final Class<T> type, final String... excludedFields) {
        EnhancedRandom randomGenerator = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().overrideDefaultInitialization(true).build();
        for (int i=0; i<DEFAULT_RANDOM_COLLECTION_SIZE; i++) {
            list.add(randomGenerator.nextObject(type, excludedFields));
        }

        return list;
    }




}
