package com.stevesouza.resttemplate.vo;

import com.stevesouza.resttemplate.TestUtils;
import com.stevesouza.resttemplate.domain.PersonCertification;
import com.stevesouza.resttemplate.utils.MiscUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class PersonCertificationVOTest {
    @Test
    public void testCertificationToCertificationVoIsRight() {
        PersonCertification entity = MiscUtils.randomData(PersonCertification.class);
        PersonCertificationVO vo = MiscUtils.convert(entity, PersonCertificationVO.class);

        assertThat(vo.getId()).isEqualTo(entity.getId());
        assertThat(vo.getLocation()).isEqualTo(entity.getLocation());
        log.info(entity.toString());
        log.info(vo.toString());

        TestUtils.assertLenientJsonEquality(vo, entity);


    }

}