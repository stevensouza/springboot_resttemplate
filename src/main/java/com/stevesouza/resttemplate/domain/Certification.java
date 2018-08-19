package com.stevesouza.resttemplate.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.stevesouza.resttemplate.vo.CertificationVO;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")

public class Certification extends EntityBase<CertificationVO> {
//    @Id
//    @GeneratedValue
//    private long id;

    private String certificationName;

    @OneToMany(mappedBy = "certification", orphanRemoval=true, cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    //@JsonIgnore
    //@JsonManagedReference
   // @JsonBackReference
    //@JsonIgnoreProperties("certification")
    //private Set<PersonCertification> peopleWithThisCertification = new HashSet<>();
    private List<PersonCertification> peopleWithThisCertification = new ArrayList<>();
}
