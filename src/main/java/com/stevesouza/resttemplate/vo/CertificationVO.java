package com.stevesouza.resttemplate.vo;

import com.stevesouza.resttemplate.domain.Certification;
import lombok.Data;


@Data
//@Entity
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class CertificationVO  extends VOBase<Certification> {
    private long id;
    private String certificationName;

//    @OneToMany(mappedBy = "certification", orphanRemoval=true, cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
//    @JsonIgnore
    //@JsonManagedReference
   // @JsonBackReference
    //@JsonIgnoreProperties("certification")
//    private List<PersonCertification> peopleWithThisCertification = new ArrayList<>();
}
