package com.stevesouza.resttemplate.service;

import com.stevesouza.resttemplate.controller.ResourceNotFound;
import com.stevesouza.resttemplate.domain.Person;
import com.stevesouza.resttemplate.repository.MyPersonColumns;
import com.stevesouza.resttemplate.repository.PersonJpaRepository;
import com.stevesouza.resttemplate.utils.MiscUtils;
import com.stevesouza.resttemplate.vo.PersonVO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Transactional
@Data
@Slf4j
public class PersonService {
    private PersonJpaRepository personJpaRepository;
    @PersistenceContext
    private EntityManager em;

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

    public List<PersonVO> selectAll() {
        TypeToken<List<PersonVO>> typeToken = new TypeToken<List<PersonVO>>(){};
        List<PersonVO> list = MiscUtils.convert(personJpaRepository.selectAll(), typeToken);
        return list;
    }

    public List<Object[]> selectColumns() {
        return personJpaRepository.selectColumns();
    }

    public List<MyPersonColumns> selectColumnsAsObject() {
        return personJpaRepository.selectColumnsAsObject();
    }

    public List<PersonVO> getAllUsersWithCertificateId(long id) {
        TypeToken<List<PersonVO>> typeToken = new TypeToken<List<PersonVO>>(){};
        List<PersonVO> list = MiscUtils.convert(personJpaRepository.getAllUsersWithCertificateId(id), typeToken);
        return list;
    }


    /**
     * input json. Note with this model phone is OneToMany and will be created each time (unique per person).
     * Certification must already exist though.
     *

     *
     * Resulting json
     * @param vo
     * {
     *     "firstName": "jeff",
     *     "lastName": "beck",
     *     "age": 77,
     *     "phones": [
     *         {
     *             "phoneNumber": "123-456-7890"
     *         },
     *         {
     *             "phoneNumber": "456-789-0123"
     *         }
     *     ],
     *     "certifications": [
     *
     *     	            {
     *                 "location": "ballston",
     *                 "certification": {
     *                     "id": 7,
     *                     "certificationName": "groovy"
     *                 }
     *             }
     *
     *     ]
     * }
     *
     * @return saved entity json. Note the id's for any of the new entities, but certification id is unchanged
     *
     * {
     *     "id": 13,
     *     "firstName": "jeff",
     *     "lastName": "beck",
     *     "age": 77,
     *     "phones": [
     *         {
     *             "id": 15,
     *             "phoneNumber": "123-456-7890"
     *         },
     *         {
     *             "id": 16,
     *             "phoneNumber": "456-789-0123"
     *         }
     *     ],
     *     "certifications": [
     *         {
     *             "id": 14,
     *             "location": "ballston",
     *             "certification": {
     *                 "id": 7,
     *                 "certificationName": "groovy"
     *             }
     *         }
     *     ]
     * }
     *
     */

    public  PersonVO create(PersonVO vo) {
        Person person = vo.toEntity();
        return  personJpaRepository.save(person).toVo();
    }

    // idempotent. returns 200 and content or 204 and no content
    public void delete(long id) {
        personJpaRepository.deleteById(id);
    }

    public PersonVO get(long id) {
        return personJpaRepository.getOne(id).toVo();
    }

    public  PersonVO update(long id, PersonVO vo) {
        Person submittedPerson = vo.toEntity();
        log.info("submitted person {}", submittedPerson);
        Person updated = personJpaRepository.findById(id).map((person)->{
            submittedPerson.update(person);
            return personJpaRepository.saveAndFlush(person);
        }).orElseThrow(() -> new ResourceNotFound("id=" + id + " not found"));

        return updated.toVo();
    }


}
