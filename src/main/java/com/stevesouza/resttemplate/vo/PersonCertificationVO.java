package com.stevesouza.resttemplate.vo;

import lombok.Data;


@Data
//@Entity
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class PersonCertificationVO {
    private long id;
    private String location;
    private CertificationVO certification;
}
