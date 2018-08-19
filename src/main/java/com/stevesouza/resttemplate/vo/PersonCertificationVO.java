package com.stevesouza.resttemplate.vo;

import com.stevesouza.resttemplate.domain.PersonCertification;
import lombok.Data;


@Data
//@Entity
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class PersonCertificationVO  extends VOBase<PersonCertification> {
    private long id;
    private String location;
    private CertificationVO certification;
}
