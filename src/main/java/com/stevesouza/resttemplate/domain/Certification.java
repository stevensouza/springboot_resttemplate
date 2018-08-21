package com.stevesouza.resttemplate.domain;

import com.stevesouza.resttemplate.vo.CertificationVO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
public class Certification extends EntityBase<CertificationVO> {
    private String certificationName;

    @OneToMany(mappedBy = "certification", orphanRemoval=true, cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<PersonCertification> peopleWithThisCertification = new ArrayList<>();

    @Override
    public String toString() {
        return "Certification{" +
                "id=" + getId() + '\'' +
                "certificationName='" + certificationName + '\'' +
                '}';
    }
}
