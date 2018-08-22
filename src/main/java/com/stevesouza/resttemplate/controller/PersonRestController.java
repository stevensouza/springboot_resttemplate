package com.stevesouza.resttemplate.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.stevesouza.resttemplate.repository.MyPersonColumns;
import com.stevesouza.resttemplate.service.PersonService;
import com.stevesouza.resttemplate.vo.PersonVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
 *  HttpServletRequest, HttpServlet- Response, String, or numeric parameters that
 *  correspond to query parameters in the request, model, cookie values, HTTP request header values, or a number of other possibilities.
 *  p 172 spring in action.
 *
 * public String serveRest(@RequestBody String body, @RequestHeader HttpHeaders headers) {
 *
 * @author stevesouza
 */


@Slf4j
@RestController
@RequestMapping(value = "/person")
public class PersonRestController extends RestControllerBase<PersonVO, PersonService> {

    // can also use more standardized @Inject
    // The autowiring allows me to inject other implementations including a mock.
    // Autowire would work for any arguments in the constructor.
    // Also if there is only one constructor autowired isn't required.
    // Note the documentation doesn't make it clear if the RestTemplate should be shared or not....
    @Autowired
    public PersonRestController(PersonService service) {
        setService(service);
    }

    @GetMapping("/selectall")
    public List<PersonVO> selectAll() {
        return getService().selectAll();
    }

    @GetMapping("/selectcolumns")
    public List<Object[]> selectColumns() {
        return getService().selectColumns();
    }

    @GetMapping("/selectcolumnsasobjects")
    public List<MyPersonColumns> selectColumnsAsObject() {
        return getService().selectColumnsAsObject();
    }

    @GetMapping("/userswithcertificate/{id}")
    public List<PersonVO> getAllUsersWithCertificateId(@PathVariable("id") long id) {
        return getService().getAllUsersWithCertificateId(id);
    }

    /** note this method doesn't currently execute a patch, but just displays the request data as a map */
    @PatchMapping("/{id}")
    public  JsonNode patch(@PathVariable("id") long id, @RequestBody JsonNode json) {
        log.debug("PATCH {}",json.toString());
        log.debug("firstName: {}", json.get("firstName").asText());

        return json;
    }

}
