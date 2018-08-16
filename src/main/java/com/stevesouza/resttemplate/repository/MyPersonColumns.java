package com.stevesouza.resttemplate.repository;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyPersonColumns {

        public MyPersonColumns(long id, String firstName) {
            this.id = id;
            this.firstName = firstName;
        }

        private long id;
        private String firstName;

}
