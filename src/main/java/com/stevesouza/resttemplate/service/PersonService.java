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

import java.util.List;


@Service
@Data
@Slf4j
public class PersonService implements ServiceInt<PersonVO> {
    private PersonJpaRepository jpaRepository;

    // can also use more standardized @Inject
    // The autowiring allows me to inject other implementations including a mock.
    // Autowire would work for any arguments in the constructor.
    // Also if there is only one constructor autowired isn't required.
    // Note the documentation doesn't make it clear if the RestTemplate should be shared or not....
    @Autowired
    public PersonService(PersonJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<PersonVO> getAll() {
         TypeToken<List<PersonVO>> typeToken = new TypeToken<List<PersonVO>>(){};
         return MiscUtils.convert(jpaRepository.findAll(), typeToken);
    }

    public List<PersonVO> selectAll() {
        TypeToken<List<PersonVO>> typeToken = new TypeToken<List<PersonVO>>(){};
        return  MiscUtils.convert(jpaRepository.selectAll(), typeToken);
    }

    public List<Object[]> selectColumns() {
        return jpaRepository.selectColumns();
    }

    public List<MyPersonColumns> selectColumnsAsObject() {
        return jpaRepository.selectColumnsAsObject();
    }

    public List<PersonVO> getAllUsersWithCertificateId(long id) {
        TypeToken<List<PersonVO>> typeToken = new TypeToken<List<PersonVO>>(){};
        return MiscUtils.convert(jpaRepository.getAllUsersWithCertificateId(id), typeToken);
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

    @Override
    public  PersonVO create(PersonVO vo) {
        return  jpaRepository.save(vo.toEntity()).toVo();
    }

    // idempotent. returns 200 and content or 204 and no content
    @Override
    public void delete(long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public PersonVO get(long id) {
        return jpaRepository.getOne(id).toVo();
    }

    @Override
    public  PersonVO update(long id, PersonVO vo) {
        Person submittedEntity = vo.toEntity();
        log.info("submitted person {}", submittedEntity);
        Person updated = jpaRepository.findById(id).map(person->{
            submittedEntity.update(person);
            return jpaRepository.save(person);
        }).orElseThrow(() -> new ResourceNotFound("id=" + id + " not found"));

        return updated.toVo();
    }


}
