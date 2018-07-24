package com.stevesouza.resttemplate.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
// The following annotation enables extra Spring boot features like the way properties are loaded
//@SpringBootTest(  classes = {MyConfiguration.class, RestSecurityConfiguration.class},
//        webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
//@SpringBootTest(  classes = {MyConfiguration.class, RestSecurityConfiguration.class},
//        webEnvironment=SpringBootTest.WebEnvironment.MOCK)
//@SpringBootTest(  classes = {MyConfiguration.class, RestSecurityConfiguration.class},
//        webEnvironment=SpringBootTest.WebEnvironment.MOCK)
//@SpringBootTest(
//        webEnvironment=SpringBootTest.WebEnvironment.MOCK)

@SpringBootTest(
        webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
public class MyExternalApiRestControllerTest {
    private Logger log = LoggerFactory.getLogger(this.getClass());


    // annotation populates with random port generated.
    // However, TestRestTemplate successfully uses the rigth port so no need to do this.
    // Just doing it for demonstration purposes.
    @LocalServerPort
    private int port;
//    @Autowired
//    private RestTemplate restTemplate;
     @Autowired
     private TestRestTemplate template;
//    private TestRestTemplate template = new TestRestTemplate();

    @Autowired
    private MyExternalApiRestController controller;


//    @Autowired
//    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testOnGoogle() throws Exception {
//        mvc.perform(get("https://jsonplaceholder.typicode.com/posts")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isUnauthorized());
        ResponseEntity<String> responseEntity = template.getForEntity("https://google.com", String.class);
        assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.MOVED_PERMANENTLY);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(301);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getHeaders().get("Location")).contains("https://www.google.com/");

        log.info("random port={}, responseEntity={}",port, responseEntity);

    }

    @Test
    public void testGetPosts() throws Exception {
        Post[] data = controller.getPostsAsObjectArray();
        assertThat(data.length).isEqualTo(100);
        Post post = data[0];
        assertThat(post.getBody()).as("Body shouldn't be null").isNotNull();
        assertThat(post.getName()).isNotNull();
        assertThat(post.getTitle()).isNotNull();
        assertThat(post.getUserId()).isNotNull();

        log.info("post.length={}, first post={}",data.length,data[0]);

    }

}