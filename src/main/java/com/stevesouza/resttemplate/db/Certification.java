package com.stevesouza.resttemplate.db;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")

public class Certification {
    @Id
    @GeneratedValue
    private long id;

    private String certificationName;

    @OneToMany(mappedBy = "certification", orphanRemoval=true, cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    //@JsonIgnore
    //@JsonManagedReference
   // @JsonBackReference
    //@JsonIgnoreProperties("certification")
    //private Set<PersonCertification> peopleWithThisCertification = new HashSet<>();
    private List<PersonCertification> peopleWithThisCertification = new ArrayList<>();
}
