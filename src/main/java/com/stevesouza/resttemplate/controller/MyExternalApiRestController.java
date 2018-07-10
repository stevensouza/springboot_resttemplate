package com.stevesouza.resttemplate.controller;


import com.stevesouza.resttemplate.db.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Note when you have a @RestController you don't need to specify the return type as @ResponseBody as that is implied.
 *
 * Note static images can be placed in various locations.  I put one in src/main/resources/static/mystaticimage.png and
 * it is served with the following url: http://localhost:8080/mystaticimage.png
 *
 * @author stevesouza
 */

@RestController
@RequestMapping(value = "/resttemplate")
public class MyExternalApiRestController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/getposts")
    // /getposts/{id} - @PathVariable("id")
    // note default for @RequestParam is true and an exception will be thrown if it isn't provided.
    public String getPosts(@RequestParam(value="id", required = false) String notUsedId) {
        RestTemplate rest = new RestTemplate();
        ResponseEntity<String> response = rest.getForEntity("https://jsonplaceholder.typicode.com/posts", String.class);
        log.info("http status code for '/getposts': " + response.getStatusCode()+ ", notUsed parameter="+notUsedId);

        return response.getBody();
    }

    @GetMapping("/getposts_aslist")
    public List<?> getPostsAsObjects() {
        RestTemplate rest = new RestTemplate();
        List<?> posts = rest.getForObject("https://jsonplaceholder.typicode.com/posts", List.class);
        log.info("executing '/getposts_aslists'");

        return posts;
    }

    @GetMapping("/getposts_asarray")
    public Post[] getPostsAsObjectArray() {
        RestTemplate rest = new RestTemplate();
        // Note I added an extra field 'name' to the Post object and populated it by default and it will show
        // up in the results.
        Post[] posts = rest.getForObject("https://jsonplaceholder.typicode.com/posts", Post[].class);
        log.info("executing '/getposts_asarray'");
        return posts;
    }
}
