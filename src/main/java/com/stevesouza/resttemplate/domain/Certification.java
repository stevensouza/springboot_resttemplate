package com.stevesouza.resttemplate.domain;

import com.stevesouza.resttemplate.vo.CertificationVO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
public class Certification extends EntityBase<CertificationVO, Certification> {
    private String certificationName;

    @OneToMany(mappedBy = "certification", fetch = FetchType.LAZY)
    private List<PersonCertification> peopleWithThisCertification = new ArrayList<>();

    @Override
    public Certification update(Certification target) {
        target.setCertificationName(getCertificationName());
        return target;
    }

    @Override
    public String toString() {
        return "Certification{" +
                "id=" + getId() + '\'' +
                "certificationName='" + certificationName + '\'' +
                '}';
    }
}
