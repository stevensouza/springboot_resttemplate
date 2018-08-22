package com.stevesouza.resttemplate.domain;

import com.stevesouza.resttemplate.vo.PersonVO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

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
public class Person extends EntityBase<PersonVO> {

    //@Max(10)
    private String firstName;
    private String lastName;


    @Min(0)
    @Max(100)
    private int age;

    @OneToMany(mappedBy = "person", orphanRemoval=true, cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    //@JoinColumn(name = "phone_id")
    private List<Phone> phones =  new ArrayList<>();

    // Note this is really modeling a many to many, but the join table is made explicit
    @OneToMany(mappedBy = "person", orphanRemoval=true, cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    //@JsonBackReference
//    @JsonIgnore
    //@JsonManagedReference
    //@JsonIgnoreProperties(value = {"person", "peopleWithThisCertification"})
    private List<PersonCertification> certifications = new ArrayList<>();
    //private Set<PersonCertification> certifications = new HashSet<>();

    public void addPhone(Phone phone) {
        phones.add(phone);
        phone.setPerson(this);

    }

    public void addPersonCertification(PersonCertification personCertification) {
        certifications.add(personCertification);
        personCertification.setPerson(this);
    }

    @Override
    public EntityBase update(EntityBase target) {
        Person personTarget = (Person) target;
        personTarget.setFirstName(getFirstName());
        personTarget.setLastName(getLastName());
        personTarget.setAge(getAge());

        personTarget.getCertifications().clear();
        personTarget.getCertifications().addAll(getCertifications());

        personTarget.getPhones().clear();
        personTarget.getPhones().addAll(getPhones());

        return personTarget;
    }
//
//    public Person update(Person target) {
//       target.setFirstName(getFirstName());
//       target.setLastName(getLastName());
//       target.setAge(getAge());
//
//       target.getCertifications().clear();
//       target.getCertifications().addAll(getCertifications());
//
//       target.getPhones().clear();
//       target.getPhones().addAll(getPhones());
//
//       return target;
//    }


    @Override
    public String toString() {
        return "Person{" +
                "id=" + getId() + '\'' +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }
}
