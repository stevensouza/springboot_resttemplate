package com.stevesouza.resttemplate.vo;

import com.stevesouza.resttemplate.domain.Person;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true)
public class PersonVO extends VOBase<Person>{
    private Long id;
    private String firstName;
    private String lastName;

    @Min(0)
    @Max(100)
    private int age;
    private List<PhoneVO>phones = new ArrayList<>();
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
