package com.stevesouza.resttemplate.vo;

import lombok.Data;

/**
Should have same basic structure as MyDbEntity
 */

@Data
public class MyDbEntityVO {
    private String firstName="Joe";
    private String lastName;
    private int age;
}
