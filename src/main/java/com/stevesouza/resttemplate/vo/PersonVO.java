package com.stevesouza.resttemplate.vo;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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

    @Min(0)
    @Max(100)
    private int age;
    private Set<PhoneVO> phones = new HashSet<>();
    private List<PersonCertificationVO> certifications = new ArrayList<>();
}
