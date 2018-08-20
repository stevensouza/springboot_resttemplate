package com.stevesouza.resttemplate.vo;

import com.stevesouza.resttemplate.domain.Certification;
import lombok.Data;


@Data
public class CertificationVO  extends VOBase<Certification> {
    private Long id;
    private String certificationName;
}
