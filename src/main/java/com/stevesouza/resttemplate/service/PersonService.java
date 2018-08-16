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

import java.util.*;

@Service
@Transactional
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
        List<Person> listDelme = personJpaRepository.findAll();
        listDelme.forEach(person -> delme(person));
        listDelme.forEach(person -> System.out.println("******"+person.getFirstName()));
        List<PersonVO> list = MiscUtils.convert(listDelme, typeToken);

       // List<PersonVO> list = MiscUtils.convert(personJpaRepository.findAll(), typeToken);
        return list;
    }

    private void delme(Person person) {
        person.getPhones().clear();
//        for (Phone phone : person.getPhones()) {
//            phone.setPerson();
//        }
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
        Person person = convertToEntity(vo);
        // must connect the person to the foreign key in PersonCertification as vo
        // doesn't have this connection.
        person.getCertifications().
                forEach(personCert -> personCert.setPerson(person));
        Person savedPerson = personJpaRepository.save(person);

        return convertToVo(savedPerson);
    }

    // idempotent. returns 200 and content or 204 and no content
    public void delete(long id) {
        personJpaRepository.deleteById(id);
    }

    public PersonVO get(long id) {
        return convertToVo(personJpaRepository.getOne(id));
    }

    public  PersonVO update(long id, PersonVO vo) {
        Person submittedPerson = convertToEntity(vo);
        log.info("submitted person {}", submittedPerson);

        submittedPerson.getCertifications().
                forEach(personCert -> personCert.setPerson(submittedPerson));
        submittedPerson.getPhones().
                forEach(phone -> phone.setPerson(submittedPerson));

        Person updated = personJpaRepository.findById(id).map((person)->{
            copyVoToEntity(submittedPerson, person);
            return personJpaRepository.saveAndFlush(person);
        }).orElseThrow(() -> new ResourceNotFound("id=" + id + " not found"));

        return convertToVo(updated);
    }

    public PersonVO convertToVo(Person person) {
        return MiscUtils.convert(person, PersonVO.class);
    }

    public Person convertToEntity(PersonVO personVo) {
        return MiscUtils.convert(personVo, Person.class);
    }

    Person copyVoToEntity(Person newEntity, Person existingEnity) {
        existingEnity.setFirstName(newEntity.getFirstName());
        existingEnity.setLastName(newEntity.getLastName());
        existingEnity.setAge(newEntity.getAge());
        existingEnity.getCertifications().clear();
        existingEnity.getCertifications().addAll(newEntity.getCertifications());

        existingEnity.getPhones().clear();
        existingEnity.getPhones().addAll(newEntity.getPhones());

/*        // phone is more complicated as i am allowing updates/inserts/deletes
        Set<Phone> existingPhones = existingEnity.getPhones();
        Set<Phone> newPhones = newEntity.getPhones();
        Map<Long, Phone> newPhonesMap = new HashMap<>();
        for (Phone phone : newPhones) {
            newPhonesMap.put(phone.getId(), phone);
        }

        // updates  - update any phone records that already exist if their phone number changed.
        for (Phone phone : existingPhones) {
            Phone newPhone = newPhonesMap.get(phone.getId());
            if (newPhone!=null && !phone.getPhoneNumber().equals(newPhone.getPhoneNumber())) {
                phone.setPhoneNumber(newPhone.getPhoneNumber());
            }
        }

        // deletes
        Set<Long> newKeys = newPhonesMap.keySet();
        // remove if phone id wasn't sent in update json and exists in database
        existingPhones.removeIf(phone -> !newKeys.contains(phone.getId()));
        Set<Phone> toInsert = newPhones.stream().filter(phone -> phone.getId()==0).collect(Collectors.toSet());
        existingPhones.addAll(toInsert);
                log.info("*****"+existingPhones);

        */
        return existingEnity;
    }




}
