package com.stevesouza.resttemplate.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;


import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
@Slf4j
public class MiscUtilsTest {

    private static final String POJO1_JSON = "{\"firstName\":\"%s\",\"lastName\":\"%s\"}";


    @Test
    public void testReadTestResource() throws IOException {
        String fileContents = MiscUtils.readResourceFile("2lines.txt");
        assertThat(fileContents).as("Should contain '01234").contains("01234");
        assertThat(fileContents).contains("56789");
    }

    @Test(expected = RuntimeException.class)
    public void testResourceDoesntExist() throws IOException {
        String fileContents = MiscUtils.readResourceFile("i_do_not_exist.txt");
    }

    @Test
    public void convert() {
        Pojo1 pojo1 = MiscUtils.randomData(Pojo1.class);
        // For this to succeed both source and destination classes must have
        // getters and setters or else the model mapper must be configured to access
        // private fields.
        Pojo2 pojo2 = MiscUtils.convert(pojo1, Pojo2.class);

        assertThat(pojo2.firstName).isEqualTo(pojo1.firstName);
        assertThat(pojo2.lastName).isEqualTo(pojo1.lastName);
    }

    @Test
    public void toJsonString() {
        Pojo1 pojo1 = MiscUtils.randomData(Pojo1.class);
        String json = MiscUtils.toJsonString(pojo1);
        String expected = String.format(POJO1_JSON, pojo1.getFirstName(), pojo1.getLastName());

        assertThat(json).isEqualTo(expected);
    }

    @Test
    public void toObject() {
        String json = String.format(POJO1_JSON, "joe", "jones");
        Pojo1 pojo1 = MiscUtils.toObject(json, Pojo1.class);

        assertThat(pojo1.getFirstName()).isEqualTo("joe");
        assertThat(pojo1.getLastName()).isEqualTo("jones");
    }

    @Data
    static class Pojo1 {
        private String firstName;
        private String lastName;
    }

    @Data
    static class Pojo2 {
        private String firstName;
        private String lastName;
        private int age;
    }

}