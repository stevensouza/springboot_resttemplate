package com.stevesouza.resttemplate.vo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
Should have same basic structure as MyDbEntity
 */

@Data
public class MyDbEntityVO {
    //private long id;

    private String firstName="Joe";
    private String lastName;
    private int age;

}
