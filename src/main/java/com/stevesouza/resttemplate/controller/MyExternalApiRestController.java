package com.stevesouza.resttemplate.controller;


import com.stevesouza.resttemplate.db.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Note when you have a @RestController you don't need to specify the return type as @ResponseBody as that is implied.
 *
 * Note static images can be placed in various locations.  I put one in src/main/resources/static/mystaticimage.png and
 * it is served with the following url: http://localhost:8080/mystaticimage.png
 *
 * RestController annotation includes both Controller and ResponseBody annotations. So @ResponseBody is not needed as an annotation
 * on the method return value.
 *
 * Note: I don't use a JpaRepository in this class, but a typical use case is to Autowire it into the constructor and call its methods
 * from the RestController.
 *
 * @author stevesouza
 */

@RestController
@RequestMapping(value = "/resttemplate")
public class MyExternalApiRestController {
    private static final String POST_URL="http://localhost:8080/postentity";
    private Logger log = LoggerFactory.getLogger(this.getClass());

    // note I could autowire here but it isn't as flexible as when you autowire in the constructor.
    private RestTemplate rest;

    // can also use more standardized @Inject
    // The autowiring allows me to inject other implementations including a mock.
    // Autowire would work for any arguments in the constructor.
    // Also if there is only one constructor autowired isn't required.
    // Note the documentation doesn't make it clear if the RestTemplate should be shared or not....
    @Autowired
    public  MyExternalApiRestController(RestTemplate rest) {
        this.rest = rest;
    }

    @GetMapping("/getposts")
    // /getposts/{id} - @PathVariable("id")
    // note default for @RequestParam is true and an exception will be thrown if it isn't provided.
    public String getPosts(@RequestParam(value="id", required = false) String notUsedId) {
        ResponseEntity<String> response = rest.getForEntity("https://jsonplaceholder.typicode.com/posts", String.class);
        log.info("http status code for '/getposts': " + response.getStatusCode()+ ", notUsed parameter="+notUsedId);

        return response.getBody();
    }

    @GetMapping("/getposts_aslist")
    public List<?> getPostsAsObjects() {
        List<?> posts = rest.getForObject("https://jsonplaceholder.typicode.com/posts", List.class);
        log.info("executing '/getposts_aslists'");

        return posts;
    }

    @GetMapping("/getposts_asarray")
    public Post[] getPostsAsObjectArray() {
        // Note I added an extra field 'name' to the Post object and populated it by default and it will show
        // up in the results.
        Post[] posts = rest.getForObject("https://jsonplaceholder.typicode.com/posts", Post[].class);
        log.info("executing '/getposts_asarray'");
        return posts;
    }

    /**
     *
     * @param post Post object to add.
     * @return Added Post object (note it will also have an id)
     */
    @PostMapping("/post")
    public Post postForObject(@RequestBody Post post) {
        // note if there were multiple arguments you still go ("first{} second{}", arg1, arg2) as it is order based.
        log.info("submitted object to create: {}", post);
        Post createdObject = rest.postForObject(POST_URL, post, Post.class);
        log.info("created object: {}", createdObject);
        return createdObject;
    }

    @GetMapping("/post")
    public String getAllPosts() {
        return rest.getForObject(POST_URL, String.class);
    }
}
