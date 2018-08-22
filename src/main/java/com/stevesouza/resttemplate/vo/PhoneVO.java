package com.stevesouza.resttemplate.vo;

import com.stevesouza.resttemplate.domain.Phone;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneVO  extends VOBase<Phone> {
    private Long id;
    private String phoneNumber;

    @Override
    public String toString() {
        return "PhoneVO{" +
                "id=" + id +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
