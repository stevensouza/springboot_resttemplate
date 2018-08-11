package com.stevesouza.resttemplate.vo;

import com.stevesouza.resttemplate.TestUtils;
import com.stevesouza.resttemplate.db.Certification;
import com.stevesouza.resttemplate.utils.MiscUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class CertificationVOTest {
    @Test
    public void testCertificationToCertificationVoIsRight() {
        Certification entity = MiscUtils.randomData(Certification.class);
        CertificationVO vo = MiscUtils.convert(entity, CertificationVO.class);

        // this tests that the known fields that should have been moved to the vo
        // match what is in the entity. Note we are only checking the direct values
        // in the class being tested here.
        assertThat(vo.getId()).isEqualTo(entity.getId());
        assertThat(vo.getCertificationName()).isEqualTo(entity.getCertificationName());

        log.info(entity.toString());
        log.info(vo.toString());

        TestUtils.assertLenientJsonEquality(vo, entity);
    }

}