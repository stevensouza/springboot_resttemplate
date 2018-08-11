package com.stevesouza.resttemplate.vo;

import lombok.Data;


@Data
//@Entity
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class CertificationVO {
    private long id;
    private String certificationName;

//    @OneToMany(mappedBy = "certification", orphanRemoval=true, cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
//    @JsonIgnore
    //@JsonManagedReference
   // @JsonBackReference
    //@JsonIgnoreProperties("certification")
//    private List<PersonCertification> peopleWithThisCertification = new ArrayList<>();
}
