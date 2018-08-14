package com.stevesouza.resttemplate.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.stevesouza.resttemplate.service.PersonService;
import com.stevesouza.resttemplate.utils.MiscUtils;
import com.stevesouza.resttemplate.vo.PersonVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
public class PersonRestController {

   // private PersonJpaRepository personJpaRepository;
    private PersonService personService;
   // private PhoneJpaRepository phoneJpaRepository;

    // can also use more standardized @Inject
    // The autowiring allows me to inject other implementations including a mock.
    // Autowire would work for any arguments in the constructor.
    // Also if there is only one constructor autowired isn't required.
    // Note the documentation doesn't make it clear if the RestTemplate should be shared or not....
    @Autowired
   // public PersonRestController(PersonService personService, PersonJpaRepository personJpaRepository, PhoneJpaRepository phoneJpaRepository) {
     public PersonRestController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<PersonVO> getAll() {
        return personService.getAll();
    }


    @GetMapping("/selectall")
    public List<PersonVO> selectAll() {
        return personService.selectAll();
    }

    @GetMapping("/userswithcertificate/{id}")
    public List<PersonVO> getAllUsersWithCertificateId(@PathVariable("id") long id) {
        return personService.getAllUsersWithCertificateId(id);
    }

    // Content-Type should be application/json and passed on from httpheaders.  methods post1, post2, post3
    // all create a mydbentity though slightly different approaches.
    // The following is probably preferred as it lets you pass in headers to the request as well as
    // return json+hal format (i.e. a string)
    @PostMapping()
    public  PersonVO post(@RequestBody PersonVO vo) {
        log.debug("POST {}",vo.toString());
        PersonVO savedPerson = personService.post(vo);
        log.debug(" CREATED {}",savedPerson.toString());
        return savedPerson;
    }

    @DeleteMapping("/{id}")
    // idempotent. returns 200 and content or 204 and no content
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id) {
        personService.delete(id);
    }

    @GetMapping("/{id}")
    public   PersonVO get(@PathVariable("id") long id) {
        return personService.get(id);
    }

    @PutMapping("/{id}")
    public  PersonVO put(@PathVariable("id") long id, @RequestBody  PersonVO vo) {
        log.debug("PUT {}",vo.toString());
        PersonVO updated = personService.put(id, vo);
        log.debug(" UPDATED {}", updated.toString());
        return updated;
    }

    /** note this method doesn't currently execute a patch, but just displays the request data as a map */
    @PatchMapping("/{id}")
    public  JsonNode patch(@PathVariable("id") long id, @RequestBody JsonNode json) {
        log.debug("PATCH {}",json.toString());
        log.debug("firstName: {}", json.get("firstName").asText());

        return json;
    }

    @GetMapping("/random")
    public PersonVO getRandom() {
        return MiscUtils.randomData(PersonVO.class);
    }

}
