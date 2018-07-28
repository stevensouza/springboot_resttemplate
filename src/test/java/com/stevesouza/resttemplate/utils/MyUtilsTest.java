package com.stevesouza.resttemplate.utils;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class MyUtilsTest {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Test
    public void testReadTestResource() throws IOException {
        String fileContents = MyUtils.readResourceFile("2lines.txt");
        assertThat(fileContents).as("Should contain '01234").contains("01234");
        assertThat(fileContents).contains("56789");
    }

    @Test(expected = RuntimeException.class)
    public void testResourceDoesntExist() throws IOException {
        String fileContents = MyUtils.readResourceFile("i_do_not_exist.txt");
        assertThat(fileContents).as("Should contain '01234").contains("01234");
        assertThat(fileContents).contains("56789");
    }


}