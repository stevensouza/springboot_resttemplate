package com.stevesouza.resttemplate.vo;

import com.stevesouza.resttemplate.domain.Phone;
import lombok.Data;

@Data
public class PhoneVO  extends VOBase<Phone> {
    private Long id;
    private String phoneNumber;
}
