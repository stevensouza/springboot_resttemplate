package com.stevesouza.resttemplate.service;

import com.stevesouza.resttemplate.TestRestBaseClass;
import com.stevesouza.resttemplate.utils.MiscUtils;
import com.stevesouza.resttemplate.vo.CertificationVO;
import io.github.benas.randombeans.api.EnhancedRandom;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@Slf4j
public class CertificationServiceIntegrationTest extends TestRestBaseClass {
    @Autowired
    private CertificationService service;


    @Before
    public void setUp()  {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void getAll() {
        List<CertificationVO> list = service.getAll();
        assertThat(list).isNotEmpty();
    }


    @Test
    public void create() throws IOException, JSONException {
        String json = MiscUtils.readResourceFile("certification-create.json");
        CertificationVO vo = MiscUtils.toObjectFromJsonString(json,  CertificationVO.class);
        CertificationVO newVo = service.create(vo);
        log.info(newVo.toString());
        assertEquals("Values of fields that exist in both json documents do not match.",
                        json, MiscUtils.toJsonString(newVo), JSONCompareMode.LENIENT);

    }

    @Test(expected = EntityNotFoundException.class)
    public void delete() {
        CertificationVO newVo = service.create(EnhancedRandom.random(CertificationVO.class));
        assertThat(service.get(newVo.getId())).isNotNull();
        service.delete(newVo.getId());
        service.get(newVo.getId()); // entity not found - throws exception
    }

    @Test
    public void get() {
        CertificationVO newVo = service.create(EnhancedRandom.random(CertificationVO.class));
        assertThat(service.get(newVo.getId())).isNotNull();
    }

    @Test
    public void update() throws IOException, JSONException {
        String json = MiscUtils.readResourceFile("certification-update.json");
        CertificationVO vo = MiscUtils.toObjectFromJsonString(json,  CertificationVO.class);
        log.info(vo.toString());
        CertificationVO updatedVo = service.update(vo.getId(), vo);
        log.info(updatedVo.toString());
        json = MiscUtils.readResourceFile("certification-update.json");
        assertEquals("Values of fields that exist in both json documents do not match.",
                json, MiscUtils.toJsonString(updatedVo), JSONCompareMode.LENIENT);
    }

}