package com.stevesouza.resttemplate.db;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.HashSet;
import java.util.Set;

/**
 *  *  @Column(name="strField3")
 *  * 	@NotNull
 *  * 	@Size(min=2, message="Name should have at least 2 characters")
 *  * 	private String str;
 *  *
 *  * 	// note this will error out if value isn't between 0 and 100 however it will
 *  * 	// only send an 500 error.  Further work must be done to send a more specific error
 *  * 	@Min(0)
 *  * 	@Max(100)
 */

@Data
@Entity
public class Person {
    @Id
    @GeneratedValue
    private long id;

    //@Max(10)
    private String firstName;
    private String lastName;


    @Min(0)
    @Max(100)
    private int age;

    @OneToMany(orphanRemoval=true, cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    //@JoinColumn(name = "phone_id")
    private Set<Phone> phones = new HashSet<>();

}
