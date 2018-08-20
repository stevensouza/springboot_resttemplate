package com.stevesouza.resttemplate.vo;

import com.stevesouza.resttemplate.domain.Certification;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=true)
public class CertificationVO  extends VOBase<Certification> {
    private Long id;
    private String certificationName;
}
