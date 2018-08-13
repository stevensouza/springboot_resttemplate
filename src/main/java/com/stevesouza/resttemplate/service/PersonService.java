package com.stevesouza.resttemplate.service;

import com.stevesouza.resttemplate.controller.ResourceNotFound;
import com.stevesouza.resttemplate.db.Person;
import com.stevesouza.resttemplate.db.PersonJpaRepository;
import com.stevesouza.resttemplate.utils.MiscUtils;
import com.stevesouza.resttemplate.vo.PersonVO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@Slf4j
public class PersonService {
    private PersonJpaRepository personJpaRepository;

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
    public  PersonVO post(PersonVO vo) {
        Person savedPerson = personJpaRepository.save(convertToEntity(vo));
        return convertToVo(savedPerson);
    }

    // idempotent. returns 200 and content or 204 and no content
    public void delete(long id) {
        personJpaRepository.deleteById(id);
    }

    public PersonVO get(long id) {
        return convertToVo(personJpaRepository.getOne(id));
    }

    public  PersonVO put(long id, PersonVO vo) {
        Person updated = personJpaRepository.findById(id).map((person)->{
            person.setFirstName(vo.getFirstName());
            return personJpaRepository.save(person);
        }).orElseThrow(() -> new ResourceNotFound("id=" + id + " not found"));

        return convertToVo(updated);
    }

    public PersonVO convertToVo(Person person) {
        return MiscUtils.convert(person, PersonVO.class);
    }

    public Person convertToEntity(PersonVO personVo) {
        return MiscUtils.convert(personVo, Person.class);
    }


}
