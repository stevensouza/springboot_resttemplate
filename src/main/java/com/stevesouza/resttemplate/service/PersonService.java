package com.stevesouza.resttemplate.service;

import com.stevesouza.resttemplate.db.PersonJpaRepository;
import com.stevesouza.resttemplate.utils.MiscUtils;
import com.stevesouza.resttemplate.vo.PersonVO;
import lombok.Data;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@Data
@Slf4j
public class PersonService {
    private PersonJpaRepository personJpaRepository;
   //private PhoneJpaRepository phoneJpaRepository;

    // can also use more standardized @Inject
    // The autowiring allows me to inject other implementations including a mock.
    // Autowire would work for any arguments in the constructor.
    // Also if there is only one constructor autowired isn't required.
    // Note the documentation doesn't make it clear if the RestTemplate should be shared or not....
    @Autowired
    public PersonService(PersonJpaRepository personJpaRepository) {
        this.personJpaRepository = personJpaRepository;
    }

    public List<PersonVO> getAll() {
        TypeToken<List<PersonVO>> typeToken = new TypeToken<List<PersonVO>>(){};
        List<PersonVO> list = MiscUtils.convert(personJpaRepository.findAll(), typeToken);
        return list;
    }


    // Content-Type should be application/json and passed on from httpheaders.  methods post1, post2, post3
    // all create a mydbentity though slightly different approaches.
    // The following is probably preferred as it lets you pass in headers to the request as well as
    // return json+hal format (i.e. a string)
    public  PersonVO post(PersonVO entity) {
//        log.debug("POST {}",entity.toString());
//        Person savedPerson = personJpaRepository.save(entity);
//        log.debug(" CREATED {}",savedPerson.toString());
//        return savedPerson;
        return null;
    }

    // idempotent. returns 200 and content or 204 and no content
    public void delete(long id) {
        personJpaRepository.deleteById(id);
    }

    public PersonVO get(@PathVariable("id") long id) {
//        Person person = personJpaRepository.getOne(id);
//////        return person;
        return null;
    }

    public  PersonVO put(long id, PersonVO entity) {
//        log.debug("PUT {}",entity.toString());
//        Person updated = personJpaRepository.findById(id).map((person)->{
//            person.setFirstName(entity.getFirstName());
//            return personJpaRepository.save(person);
//        }).orElseThrow(() -> new ResourceNotFound("id=" + id + " not found"));
//
//        //Person savedPerson = personJpaRepository.save(entity);
//        log.debug(" UPDATED {}", updated.toString());
//        return updated;
        return null;
    }


}
