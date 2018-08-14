package com.stevesouza.resttemplate.controller;

import com.stevesouza.resttemplate.TestRestBaseClass;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;


// The following annotation enables extra Spring boot features like the way properties are loaded
//@SpringBootTest(  classes = {MyConfiguration.class, RestSecurityConfiguration.class},
//        webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
//@SpringBootTest(  classes = {MyConfiguration.class, RestSecurityConfiguration.class},
//        webEnvironment=SpringBootTest.WebEnvironment.MOCK)
//@SpringBootTest(  classes = {MyConfiguration.class, RestSecurityConfiguration.class},
//        webEnvironment=SpringBootTest.WebEnvironment.MOCK)
//@SpringBootTest(
//        webEnvironment=SpringBootTest.WebEnvironment.MOCK)

//@AutoConfigureMockMvc
public class MyExternalApiRestControllerTest extends TestRestBaseClass {

    @Autowired
    private MyExternalApiRestController controller;


//    @Autowired
//    private MockMvc mvc;

    @Before
    public void setUp()  {
    }

    @After
    public void tearDown()  {
    }

    @Test
    public void testOnGoogle() {
//        mvc.perform(get("https://jsonplaceholder.typicode.com/posts")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isUnauthorized());
        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity("https://google.com", String.class);
        assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.MOVED_PERMANENTLY);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(301);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getHeaders().get("Location")).contains("https://www.google.com/");

        log.info("random port={}, responseEntity={}",getPort(), responseEntity);

    }

    @Test
    public void testGetPosts() {
        Post[] data = controller.getPostsAsObjectArray();
        assertThat(data.length).isNotZero();
        Post post = data[0];
        assertThat(post.getBody()).as("Body shouldn't be null").isNotNull();
        assertThat(post.getName()).isNotNull();
        assertThat(post.getTitle()).isNotNull();
        assertThat(post.getUserId()).isNotNull();

        log.info("post.length={}, first post={}",data.length,data[0]);

    }

}