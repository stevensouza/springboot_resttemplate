package com.stevesouza.resttemplate.domain;

import com.stevesouza.resttemplate.vo.PhoneVO;
import lombok.EqualsAndHashCode;
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
public class Phone extends EntityBase<PhoneVO, Phone> {
    private String phoneNumber;

    // I had to exclude this from lomboks autogenerated equals/hashtag calculation as it
    // would do an infinite loop between phone and person.
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private Person person;

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + getId() + '\'' +
                "phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

}
