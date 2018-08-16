package com.stevesouza.resttemplate.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class PersonCertification {
    @Id
    @GeneratedValue
    private long id;

    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
//    @ManyToOne // default fetch is eager
   // @JsonIgnore
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    //@ManyToOne // default fetch is eager
   // @JsonIgnore
    private Certification certification;

}
