package com.stevesouza.resttemplate.controller;


import com.stevesouza.resttemplate.domain.Certification;
import com.stevesouza.resttemplate.repository.CertificationJpaRepository;
import com.stevesouza.resttemplate.repository.MyPersonColumns;
import com.stevesouza.resttemplate.service.PersonService;
import com.stevesouza.resttemplate.utils.MiscUtils;
import com.stevesouza.resttemplate.vo.CertificationVO;
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
 *  HttpServletRequest, HttpServlet- Response, String, or numeric parameters that
 *  correspond to query parameters in the request, model, cookie values, HTTP request header values, or a number of other possibilities.
 *  p 172 spring in action.
 *
 * public String serveRest(@RequestBody String body, @RequestHeader HttpHeaders headers) {
 *
 * @author stevesouza
 */


@RestController
@RequestMapping(value = "/certifications")
@Slf4j
public class CertificationRestController {

    private CertificationJpaRepository certificationJpaRepository;

    // can also use more standardized @Inject
    // The autowiring allows me to inject other implementations including a mock.
    // Autowire would work for any arguments in the constructor.
    // Also if there is only one constructor autowired isn't required.
    // Note the documentation doesn't make it clear if the RestTemplate should be shared or not....
    @Autowired
    public CertificationRestController(CertificationJpaRepository certificationJpaRepository) {
        this.certificationJpaRepository = certificationJpaRepository;
    }

    @GetMapping
    public List<Certification> getAll() {
        return certificationJpaRepository.findAll();
    }


    @GetMapping("/{id}")
    public   Certification get(@PathVariable("id") long id) {
        Certification certification = certificationJpaRepository.getOne(id);
        return certification;
    }

    @GetMapping("/random")
    public Certification getRandom() {
        return MiscUtils.randomData(Certification.class);
    }

    // Content-Type should be application/json and passed on from httpheaders.  methods post1, post2, post3
    // all create a mydbentity though slightly different approaches.
    // The following is probably preferred as it lets you pass in headers to the request as well as
    // return json+hal format (i.e. a string)
//    @Override
//    @PostMapping()
//    public CertificationVO post(@RequestBody CertificationVO vo) {
//        log.debug("POST {}",vo.toString());
//      //  PersonVO savedPerson = personService.create(vo);
//        log.debug(" CREATED {}",entity.toString());
//        return entity;
//    }
//
//    @Override
//    @DeleteMapping("/{id}")
//    // idempotent. returns 200 and content or 204 and no content
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void delete(@PathVariable("id") long id) {
//        certificationService.delete(id);
//    }
//
//    @Override
//    @GetMapping("/{id}")
//    public   PersonVO get(@PathVariable("id") long id) {
//        return certificationService.get(id);
//    }
//
//    @Override
//    @PutMapping("/{id}")
//    public  PersonVO put(@PathVariable("id") long id, @RequestBody PersonVO vo) {
//        log.debug("PUT {}",vo.toString());
//        PersonVO updated = personService.update(id, vo);
//        log.debug(" UPDATED {}", updated.toString());
//        return updated;
//    }

}
