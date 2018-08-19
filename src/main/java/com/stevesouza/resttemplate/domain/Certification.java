package com.stevesouza.resttemplate.domain;

import com.stevesouza.resttemplate.vo.CertificationVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Certification extends EntityBase<CertificationVO> {
    private String certificationName;

    @OneToMany(mappedBy = "certification", orphanRemoval=true, cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<PersonCertification> peopleWithThisCertification = new ArrayList<>();
}
