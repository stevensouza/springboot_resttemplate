package com.stevesouza.resttemplate.vo;

import com.stevesouza.resttemplate.db.Person;
import lombok.Data;

import javax.persistence.*;

@Data
public class PhoneVO {
    private long id;
    private String phoneNumber;
}
