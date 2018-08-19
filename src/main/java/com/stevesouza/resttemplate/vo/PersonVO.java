package com.stevesouza.resttemplate.vo;

import com.stevesouza.resttemplate.domain.Person;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@EqualsAndHashCode(callSuper = true)
public class PersonVO extends VOBase<Person>{
    private long id;
    private String firstName;
    private String lastName;

    @Min(0)
    @Max(100)
    private int age;
    private Set<PhoneVO> phones = new HashSet<>();
    private List<PersonCertificationVO> certifications = new ArrayList<>();

    @Override
    public Person toEntity() {
        Person person = super.toEntity();
        person.getCertifications().
                    forEach(personCert -> personCert.setPerson(person));
        person.getPhones().
                    forEach(phone -> phone.setPerson(person));
        return person;
    }
}
