package com.stevesouza.resttemplate.vo;

import com.stevesouza.resttemplate.domain.Phone;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class PhoneVO  extends VOBase<Phone> {
    private Long id;
    private String phoneNumber;
}
