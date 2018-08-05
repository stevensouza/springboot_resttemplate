package com.stevesouza.resttemplate.controller;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.stevesouza.resttemplate.db.MyDbEntity;
import com.stevesouza.resttemplate.utils.MiscUtils;
import com.stevesouza.resttemplate.vo.MyDbEntityVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
 *  HttpServletRequest, HttpServlet- Response, String, or numeric parameters that
 *  correspond to query parameters in the request, model, cookie values, HTTP request header values, or a number of other possibilities.
 *  p 172 spring in action.
 *
 * public String serveRest(@RequestBody String body, @RequestHeader HttpHeaders headers) {
 *
 * @author stevesouza
 */

/** NOTE CURRENTLY THE REST CALLS TO RestTemplate DON'T PASS ON ANY AUTHENTICATION INFORMATION
 * Basic auth HEADERS MUST BE PASSED ON. SEE THE patch METHOD FOR AN EXAMPLE THAT DOES
 * SUCCESSFULLY PASS THIS LOGIN INFORMATION ON VIA RestTemplate.exchange 8/5/18
 *
 * ALSO NOTE TEST GENERATES A PORT AUTOMATICALLY AND THIS CODE HARDCODES 8080
  */

@RestController
@RequestMapping(value = "/mydbentityrest")
public class MyDbEntityRestController {
    private static final String BASE_URL = "http://localhost:8080/api/mydbentity";
    private Logger log = LoggerFactory.getLogger(this.getClass());

    // note I could autowire here but it isn't as flexible as when you autowire in the constructor.
    private RestTemplate rest;

    // can also use more standardized @Inject
    // The autowiring allows me to inject other implementations including a mock.
    // Autowire would work for any arguments in the constructor.
    // Also if there is only one constructor autowired isn't required.
    // Note the documentation doesn't make it clear if the RestTemplate should be shared or not....
    @Autowired
    public MyDbEntityRestController(RestTemplate rest) {
        this.rest = rest;
    }


    // Content-Type should be application/json and passed on from httpheaders.  methods post1, post2, post3
    // all create a mydbentity though slightly different approaches.
    // The following is probably preferred as it lets you pass in headers to the request as well as
    // return json+hal format (i.e. a string)
    @PostMapping("/mydbentity")
    public  ResponseEntity<String> post1(@RequestBody MyDbEntity entity, @RequestHeader MultiValueMap<String, String> headers) {
        // note if there were multiple arguments you still go ("first{} second{}", arg1, arg2) as it is order based.
        log.debug("submitted httpheaders: {}", headers);

        // doing this as i need to pass on the headers
        HttpEntity<MyDbEntity> requestEntity = new HttpEntity<>(entity, headers);
        ResponseEntity<String> responseEntity = rest.exchange(BASE_URL, HttpMethod.POST, requestEntity, String.class);

        log.debug("POST ResponseEntity: {}", responseEntity);
        return responseEntity;
    }

    /**
     *
     * @param entity entity object to add.
     * @return ResponseEntity including added object
     */
    @PostMapping("/mydbentity/post2")
    // not idempotent.
    // http status code 201 Set the Location header to contain a link to the newly-created
    // resource (on POST). Response body content may or may not be present.
    // @ResponseStatus(HttpStatus.CREATED)
    // in this case i simply return the status code from the underlying api
    // note I return Post object, but String could just as easily be returned for raw json.
    // note this post doesn't pass on headers and so regular json is returned.

    // note this returns a MyDbEntity in the ResonseEntity. The result is that they hal links are
    // stripped out and regular json is returned.
    public  ResponseEntity<MyDbEntity> post2(@RequestBody MyDbEntity entity) {
        // note if there were multiple arguments you still go ("first{} second{}", arg1, arg2) as it is order based.
        log.debug("submitted object to create: {}", entity);
        ResponseEntity<MyDbEntity> responseEntity = rest.postForEntity(BASE_URL, entity, MyDbEntity.class);
        // MyDbEntity createdObject = rest.postForObject(BASE_URL, entity, MyDbEntity.class);
        // MyDbEntity createdObject = responseEntity.getBody();
        log.debug("POST ResponseEntity: {}", responseEntity);
        return responseEntity;
        //return createdObject;
    }

    @PostMapping("/mydbentity/post3")
    // not idempotent.
    // http status code 201 Set the Location header to contain a link to the newly-created
    // resource (on POST). Response body content may or may not be present.
    // @ResponseStatus(HttpStatus.CREATED)
    // in this case i simply return the status code from the underlying api
    // note I return Post object, but String could just as easily be returned for raw json.
    // note this post doesn't pass on headers and so regular json is returned.

    // same approach as post2 except a ResponseEntity<String> is returned instead of
    // ResponseEntity<MyDbEntity>.  The result is that a string of json including data and
    // hal links is returned instead of just plain json.
    public  ResponseEntity<String> post3(@RequestBody MyDbEntity entity) {
        // note if there were multiple arguments you still go ("first{} second{}", arg1, arg2) as it is order based.
        log.debug("submitted object to create: {}", entity);
        ResponseEntity<String> responseEntity = rest.postForEntity(BASE_URL, entity, String.class);
        // MyDbEntity createdObject = rest.postForObject(BASE_URL, entity, MyDbEntity.class);
        // MyDbEntity createdObject = responseEntity.getBody();
        log.debug("POST ResponseEntity: {}", responseEntity);
        return responseEntity;
        //return createdObject;
    }


    @GetMapping("/mydbentity")
    public String getAll(RequestEntity<String> requestEntity) {
        log.info(requestEntity.toString());
        ResponseEntity<String> responseEntity = rest.exchange(BASE_URL , HttpMethod.GET
                , requestEntity, String.class);
        return responseEntity.getBody();

      //  return rest.getForObject(BASE_URL, String.class);
    }

    @GetMapping("/mydbentity/{id}")
    public String get(@PathVariable("id") long id) {
        return rest.getForObject(BASE_URL +"/"+id, String.class);
    }

    @DeleteMapping("/mydbentity/{id}")
    // idempotent. returns 200 and content or 204 and no content
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id) {
        rest.delete(BASE_URL +"/"+id);
    }

    @PutMapping("/mydbentity/{id}")
    // idempotent. http status code 204 good for delete and put. 200 if you return content
    // @ResponseStatus(HttpStatus.NO_CONTENT)
    // in the following code i simply return what the underlying api returns for a status code
    // which is 200
    public ResponseEntity<String> put(@PathVariable("id") long id, @RequestBody MyDbEntity updatedEntity) {
        // This could also be used but i want to return the requests headers for 'Location' so
        // returning ResponseEntity.
        // ResponseEntity<MyDbEntity> responseEntity = rest.put(BASE_URL, updatedEntity, MyDbEntity.class); this works too.  just doesn't return underlying api's headers
        // note i Use Post.class but String.class (raw json) could also be used.
        HttpEntity<MyDbEntity> requestEntity = new HttpEntity<>(updatedEntity);
        ResponseEntity<String> responseEntity = rest.exchange(BASE_URL +"/"+id, HttpMethod.PUT, requestEntity, String.class);
        log.debug("PUT responseEntity={}", responseEntity);
        return responseEntity;

       // rest.put(BASE_URL+"/"+id, updatedEntity);
    }

    @PatchMapping("/mydbentity/{id}")
    // @ResponseStatus(HttpStatus.NO_CONTENT) // can provide just changed fields.
    // Note I don't convert to the underlying POJO here and leave the data in json format.
    public ResponseEntity<String>  patch(@PathVariable("id") long id, RequestEntity<String> requestEntity) {
        log.debug("RequestEntity= "+requestEntity);
        ResponseEntity<String> responseEntity = rest.exchange(BASE_URL +"/"+id, HttpMethod.PATCH, requestEntity, String.class);
        log.debug("PATCH responseEntity={}", responseEntity);
        return responseEntity;
    }

    // needs to have Accept: text/plain or error. I think could also use consumes arg to @GetMapping
    // to not call this method unless that is done.
    //     @GetMapping(path="argplay",  produces = "text/plain") - note if you pass in Accept: application/json an error would occur http status 406

    // without a produces arg it will try to respond if it can.
    // note path argument is not required could just put in "argplay".  I was explicit as a learning
    // exercise.
    @GetMapping(path="argplay/{pathvariable}")
    public String argPlay(
            @RequestHeader HttpHeaders httpHeaders,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session,
            WebRequest webRequest,
            @PathVariable("pathvariable") String pathVariable,
            @RequestParam(name="param", defaultValue = "hello world") String param,
            @CookieValue(name="mycookie", defaultValue="mydefaultCookieValue") String myCookie,
            @RequestHeader("accept") String acceptHeader
           ){
        String formatStr = "param={0} \n httpheaders={1} \n request={2}\n response={3}\n cookie={4} \n acceptHeader={5}\n pathVariable={6}\n session={7}\n webrequest={8}\n";
        String str =
                MessageFormat.format(formatStr,
                param, httpHeaders, request.toString(), response, myCookie, acceptHeader, pathVariable, session, webRequest);
        log.debug(str);
        return str;
    }

    @GetMapping(path="vo")
    public MyDbEntityVO getVo() {
        MyDbEntity entity = MiscUtils.randomData(MyDbEntity.class);
        log.debug(entity.toString());
        MyDbEntityVO vo = MiscUtils.convert(entity, MyDbEntityVO.class);
        log.debug(vo.toString());
        return vo;
    }

    @GetMapping(path="random")
    public DelmeVO getRandom() {
        Delme delme = MiscUtils.randomData(Delme.class);
        log.debug(delme.toString());
        DelmeVO delmeVo = MiscUtils.convert(delme, DelmeVO.class);
        log.debug(delmeVo.toString());
        return delmeVo;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Slf4j
    public static class Delme {
        private String firstName;
        private String lastName;
        @JsonProperty("mydetails")
        private Set<DelmeDetail> set;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Slf4j
    public static class DelmeVO {
        private String firstName;
        private String lastName;
        @JsonProperty("mydetails")
        private Set<DelmeVODetail> set;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Slf4j
    public static class DelmeDetail {
        private String detail;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Slf4j
    public static class DelmeVODetail {
        private String detail;
    }



}
