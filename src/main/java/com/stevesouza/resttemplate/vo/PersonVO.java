package com.stevesouza.resttemplate.vo;

import com.stevesouza.resttemplate.db.Phone;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
//@Entity
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")

public class PersonVO {
    private long id;
    private String firstName;
    private String lastName;
    private int age;
    private Set<Phone> phones = new HashSet<>();
    private List<PersonCertificationVO> certifications = new ArrayList<>();
}
