package com.stevesouza.resttemplate.controller;


import com.stevesouza.resttemplate.db.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
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
    // not idempotent.
    // http status code 201 Set the Location header to contain a link to the newly-created
    // resource (on POST). Response body content may or may not be present.
    // @ResponseStatus(HttpStatus.CREATED)
    // in this case i simply return the status code from the underlying api
    // note I return Post object, but String could just as easily be returned for raw json.
    public  ResponseEntity<Post> postForObject(@RequestBody Post post) {
        //public Post postForObject(@RequestBody Post post) {
        // note if there were multiple arguments you still go ("first{} second{}", arg1, arg2) as it is order based.
        log.info("submitted object to create: {}", post);
        ResponseEntity<Post> responseEntity = rest.postForEntity(POST_URL, post, Post.class);
        //Post createdObject = rest.postForObject(POST_URL, post, Post.class);
        // Post createdObject = responseEntity.getBody();
        log.info("Post ResponseEntity: {}", responseEntity);
        return responseEntity;
        //return createdObject;
    }

    @GetMapping("/post/{id}")
    public String getPost(@PathVariable("id") long id) {
        return rest.getForObject(POST_URL+"/"+id, String.class);
    }

    @DeleteMapping("/post/{id}")
    // idempotent. returns 200 and content or 204 and no content
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable("id") long id) {
        rest.delete(POST_URL+"/"+id);
    }

    @PutMapping("/post/{id}")
    // idempotent. http status code 204 good for delete and put. 200 if you return content
    // @ResponseStatus(HttpStatus.NO_CONTENT)
    // in the following code i simply return what the underlying api returns for a status code
    // which is 200
    public ResponseEntity<String> putPost(@PathVariable("id") long id, @RequestBody Post updatedEntity) {
        // This could also be used but i want to return the requests headers for 'Location' so
        // returning ResponseEntity.
        // ResponseEntity<Post> responseEntity = rest.put(POST_URL, post, Post.class); this works too.  just doesn't return underlying api's headers
        // note i Use Post.class but String.class (raw json) could also be used.
        HttpEntity<Post> requestEntity = new HttpEntity<>(updatedEntity);
        ResponseEntity<String> responseEntity = rest.exchange(POST_URL+"/"+id, HttpMethod.PUT, requestEntity, String.class);
        log.info("Put responseEntity={}", responseEntity);
        return responseEntity;

       // rest.put(POST_URL+"/"+id, updatedEntity);
    }

    @PatchMapping("/post/{id}")
    // @ResponseStatus(HttpStatus.NO_CONTENT) // can provide just changed fields.
    // Note I don't convert to the underlying POJO here and leave the data in json format.
    public ResponseEntity<String>  patchPost(@PathVariable("id") long id, RequestEntity<String> requestEntity) {
        log.info("RequestEntity= "+requestEntity);
        ResponseEntity<String> responseEntity = rest.exchange(POST_URL+"/"+id, HttpMethod.PATCH, requestEntity, String.class);
        log.info("Patch responseEntity={}", responseEntity);
        return responseEntity;
    }


    @GetMapping("/post")
    public String getAllPosts() {
        return rest.getForObject(POST_URL, String.class);
    }
}
