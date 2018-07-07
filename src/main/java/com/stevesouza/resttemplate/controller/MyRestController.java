package com.stevesouza.resttemplate.controller;


import com.stevesouza.resttemplate.db.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.List;

/**
 * Note when you have a @RestController you don't need to specify the return type as @ResponseBody as that is implied.
 *
 */

@RestController
@RequestMapping(value="/resttemplate")
public class MyRestController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/getposts")
    // @PathVariable("job")
    public String getPosts() {
    	 RestTemplate rest = new RestTemplate();
    	 ResponseEntity<String> response = rest.getForEntity("https://jsonplaceholder.typicode.com/posts", String.class);
    	 log.info("http status code: "+response.getStatusCode());
    	 
    	 return response.getBody();
    }

    @GetMapping("/getpostsasobjects")
    public List<?> getPostsAsObjects() {
        RestTemplate rest = new RestTemplate();
        List<?> posts = rest.getForObject("https://jsonplaceholder.typicode.com/posts", List.class);
        return posts;
    }

    @GetMapping("/getpostsdelme")
    public Post[] getPostsAsObjectArray() {
        RestTemplate rest = new RestTemplate();
        // Note I added an extra field 'name' to the Post object and populated it by default and it will show
        // up in the results.
        Post[] posts = rest.getForObject("https://jsonplaceholder.typicode.com/posts", Post[].class);
        return posts;
    }
}
