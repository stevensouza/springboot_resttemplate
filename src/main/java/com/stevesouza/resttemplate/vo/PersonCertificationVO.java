package com.stevesouza.resttemplate.vo;

import com.stevesouza.resttemplate.domain.PersonCertification;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PersonCertificationVO  extends VOBase<PersonCertification> {
    private Long id;
    private String location;
    private CertificationVO certification;

    @Override
    public String toString() {
        return "PersonCertificationVO{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", certification=" + certification +
                '}';
    }
}
