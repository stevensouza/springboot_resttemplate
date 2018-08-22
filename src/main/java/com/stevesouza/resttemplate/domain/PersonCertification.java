package com.stevesouza.resttemplate.domain;

import com.stevesouza.resttemplate.vo.PersonCertificationVO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

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

@Getter
@Setter
@Entity
public class PersonCertification extends EntityBase<PersonCertificationVO, PersonCertification> {
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    //  not required but can do this. points to parent tables primary key: @JoinColumn(name="person_id")
    //    @ManyToOne // default fetch is eager
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    private Certification certification;

    @Override
    public String toString() {
        return "PersonCertification{" +
                "id=" + getId() + '\'' +
                "location='" + location + '\'' +
                '}';
    }
}
