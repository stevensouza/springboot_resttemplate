package com.stevesouza.resttemplate.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.stevesouza.resttemplate.db.Person;
import com.stevesouza.resttemplate.db.PersonJpaRepository;
import com.stevesouza.resttemplate.db.PhoneJpaRepository;
import com.stevesouza.resttemplate.utils.MiscUtils;
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


@RestController
@RequestMapping(value = "/person")
@Slf4j
public class PersonRestController {

    private PersonJpaRepository personJpaRepository;
    private PhoneJpaRepository phoneJpaRepository;

    // can also use more standardized @Inject
    // The autowiring allows me to inject other implementations including a mock.
    // Autowire would work for any arguments in the constructor.
    // Also if there is only one constructor autowired isn't required.
    // Note the documentation doesn't make it clear if the RestTemplate should be shared or not....
    @Autowired
    public PersonRestController(PersonJpaRepository personJpaRepository, PhoneJpaRepository phoneJpaRepository) {
        this.personJpaRepository = personJpaRepository;
        this.phoneJpaRepository = phoneJpaRepository;
    }

    @GetMapping
    public List<Person> getAll() {
        return personJpaRepository.findAll();
    }


    // Content-Type should be application/json and passed on from httpheaders.  methods post1, post2, post3
    // all create a mydbentity though slightly different approaches.
    // The following is probably preferred as it lets you pass in headers to the request as well as
    // return json+hal format (i.e. a string)
    @PostMapping()
    public  Person post(@RequestBody Person entity) {
        log.debug("POST {}",entity.toString());
        Person savedPerson = personJpaRepository.save(entity);
        log.debug(" CREATED {}",savedPerson.toString());
        return savedPerson;
    }

    @DeleteMapping("/{id}")
    // idempotent. returns 200 and content or 204 and no content
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id) {
        personJpaRepository.deleteById(id);
    }

    @GetMapping("/{id}")
    public   Person get(@PathVariable("id") long id) {
        Person person = personJpaRepository.getOne(id);
        return person;
    }

    @PutMapping("/{id}")
    public  Person put(@PathVariable("id") long id, @RequestBody  Person entity) {
        log.debug("PUT {}",entity.toString());
        Person updated = personJpaRepository.findById(id).map((person)->{
            person.setFirstName(entity.getFirstName());
            return personJpaRepository.save(person);
        }).orElseThrow(() -> new ResourceNotFound("id=" + id + " not found"));

        //Person savedPerson = personJpaRepository.save(entity);
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
    public Person getRandom() {
        return MiscUtils.randomData(Person.class);
    }

}
