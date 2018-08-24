package com.stevesouza.resttemplate.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Note when you have a @RestController you don't need to specify the return type as @ResponseBody as that is implied.
 * <p>
 * Note static images can be placed in various locations.  I put one in src/main/resources/static/mystaticimage.png and
 * it is served with the following url: http://localhost:8080/mystaticimage.png
 * <p>
 * RestController annotation includes both Controller and ResponseBody annotations. So @ResponseBody is not needed as an annotation
 * on the method return value.
 * <p>
 * Note: I don't use a JpaRepository in this class, but a typical use case is to Autowire it into the constructor and call its methods
 * from the RestController.
 *
 * @author stevesouza
 */

@RestController
@RequestMapping(value = "/resttemplate")
public class MyExternalApiRestController {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/posts";
    private Logger log = LoggerFactory.getLogger(this.getClass());

    // note I could autowire here but it isn't as flexible as when you autowire in the constructor.
    private RestTemplate rest;

    // can also use more standardized @Inject
    // The autowiring allows me to inject other implementations including a mock.
    // Autowire would work for any arguments in the constructor.
    // Also if there is only one constructor autowired isn't required.
    // Note the documentation doesn't make it clear if the RestTemplate should be shared or not....
    @Autowired
    public MyExternalApiRestController(RestTemplate rest) {
        this.rest = rest;
    }

    @GetMapping("/getposts")
    // /getposts/{id} - @PathVariable("id")
    // note default for @RequestParam is true and an exception will be thrown if it isn't provided.
    // This id isn't used, but just passed to show the syntax.
    public String getPosts(@RequestParam(value = "id", required = false) String notUsedId) {
        ResponseEntity<String> response = rest.getForEntity(BASE_URL, String.class);
        log.info("http status code for '/getposts': {}, notUsed parameter={}", response.getStatusCode(), notUsedId);

        return response.getBody();
    }

    @GetMapping("/getposts_aslist")
    public List<?> getPostsAsObjects() {
        List<?> posts = rest.getForObject(BASE_URL, List.class);
        log.info("executing '/getposts_aslists'");
        return posts;
    }

    @GetMapping("/getposts2_aslistobjects")
    public MyList getPost2sAsObjects() {
        // see if better way https://stackoverflow.com/questions/6173182/spring-json-convert-a-typed-collection-like-listmypojo
        // google search:
        MyList posts = rest.getForObject(BASE_URL,  MyList.class);
        log.info("executing '/getposts2_aslistobjects'");
        return posts;
    }

    @GetMapping("/getposts_asarray")
    public Post[] getPostsAsObjectArray() {
        // Note I added an extra field 'name' to the Post object and populated it by default and it will show
        // up in the results.
        Post[] posts = rest.getForObject(BASE_URL, Post[].class);
        log.info("executing '/getposts_asarray'");
        return posts;
    }

    public static class MyList extends ArrayList<Post2> { }


}
