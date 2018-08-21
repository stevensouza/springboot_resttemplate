package com.stevesouza.resttemplate.vo;

import com.stevesouza.resttemplate.domain.Certification;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CertificationVO  extends VOBase<Certification> {
    private Long id;
    private String certificationName;
}
