package com.stevesouza.resttemplate.vo;

import com.stevesouza.resttemplate.TestUtils;
import com.stevesouza.resttemplate.db.Certification;
import com.stevesouza.resttemplate.db.Person;
import com.stevesouza.resttemplate.db.PersonCertification;
import com.stevesouza.resttemplate.utils.MiscUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class PersonVOTest {
    @Test
    public void testPersonToPersonVoIsRight() {
        Person entity = MiscUtils.randomData(Person.class);
        PersonCertification personCertification = MiscUtils.randomData(PersonCertification.class);
        entity.getCertifications().add(personCertification);
        PersonVO vo = MiscUtils.convert(entity, PersonVO.class);

        assertThat(vo.getId()).isEqualTo(entity.getId());
        assertThat(vo.getFirstName()).isEqualTo(entity.getFirstName());
        assertThat(vo.getAge()).isEqualTo(entity.getAge());

        PersonCertificationVO actualPersonCertVo = vo.getCertifications().get(0);
        PersonCertification expectedPersonCert = entity.getCertifications().get(0);
        assertThat(actualPersonCertVo.getId()).isEqualTo(expectedPersonCert.getId());
        assertThat(actualPersonCertVo.getLocation()).isEqualTo(expectedPersonCert.getLocation());

        CertificationVO actualCertVo = vo.getCertifications().get(0).getCertification();
        Certification expectedCert = entity.getCertifications().get(0).getCertification();
        assertThat(actualCertVo.getId()).isEqualTo(expectedCert.getId());
        assertThat(actualCertVo.getCertificationName()).isEqualTo(expectedCert.getCertificationName());


        log.info(entity.toString());
        log.info(vo.toString());

        TestUtils.assertLenientJsonEquality(vo, entity);
    }

}