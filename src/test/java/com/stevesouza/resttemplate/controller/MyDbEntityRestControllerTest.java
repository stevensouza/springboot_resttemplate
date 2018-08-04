package com.stevesouza.resttemplate.controller;

import com.stevesouza.resttemplate.TestRestBaseClass;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;


public class MyDbEntityRestControllerTest extends TestRestBaseClass {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void post1() {
    }

    @Test
    public void post2() {
    }

    @Test
    public void post3() {
    }

    @Test
    public void getAll() {
    }

    @Test
    public void get() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void put() {
    }

    @Test
    public void patch() {
    }

    @Test
    //@WithUserDetails("craig")
    public void argPlay() {
        ResponseEntity<String> responseEntity =
                getTestRestTemplate("user", "user").
                getForEntity(BASE_URL+"/mydbentityrest/argplay/mypathvariable", String.class);
        log.info(responseEntity.toString());
    }

    @Test
    public void needToAuthenticate() {
        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(BASE_URL+"/mydbentityrest/argplay/mypathvariable", String.class);
        assertThat(responseEntity.getStatusCodeValue()).as("Must be authenticated").isEqualTo(302);
    }

    @Test
    public void invalidUser() {
        ResponseEntity<String> responseEntity = getTestRestTemplate("invalid_user", "user").getForEntity(BASE_URL+"/mydbentityrest/argplay/mypathvariable", String.class);
        assertThat(responseEntity.getStatusCodeValue()).as("Incorrect user").isEqualTo(302);
    }

    @Test
    public void invalidPassword() {
        ResponseEntity<String> responseEntity = getTestRestTemplate("user", "invalid_password").getForEntity(BASE_URL+"/mydbentityrest/argplay/mypathvariable", String.class);
        assertThat(responseEntity.getStatusCodeValue()).as("Incorrect user").isEqualTo(302);
    }

    @Test
    public void getRandom() {
        ResponseEntity<String> responseEntity =
                getTestRestTemplate("user", "user").
                        getForEntity(BASE_URL+"/mydbentityrest/random", String.class);
        log.info(responseEntity.toString());
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

}