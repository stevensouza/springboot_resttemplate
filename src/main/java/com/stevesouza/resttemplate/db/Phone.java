package com.stevesouza.resttemplate.db;

import lombok.*;

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
//@Getter
//@Setter
//@RequiredArgsConstructor
//@EqualsAndHashCode
@Entity
public class Phone {
    @Id
    @GeneratedValue
    private long id;

    private String phoneNumber;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private Person person;

}
