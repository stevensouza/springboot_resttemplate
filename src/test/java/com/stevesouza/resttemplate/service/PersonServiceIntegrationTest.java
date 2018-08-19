package com.stevesouza.resttemplate.service;

import com.stevesouza.resttemplate.TestRestBaseClass;
import com.stevesouza.resttemplate.utils.MiscUtils;
import com.stevesouza.resttemplate.vo.PersonVO;
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
public class PersonServiceIntegrationTest extends TestRestBaseClass {
    @Autowired
    private PersonService personService;


    @Before
    public void setUp()  {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void getAll() {
        List<PersonVO> personVOList = personService.getAll();
        assertThat(personVOList).isNotEmpty();
    }

    @Test
    public void selectAll() {
        List<PersonVO> personVOList = personService.selectAll();
        assertThat(personVOList).isNotEmpty();
    }

    @Test
    public void create() throws IOException, JSONException {
        String json = MiscUtils.readResourceFile("person-create.json");
        PersonVO vo = MiscUtils.toObjectFromJsonString(json,  PersonVO.class);
        PersonVO newVo = personService.create(vo);
        log.info(newVo.toString());
        assertEquals("Values of fields that exist in both json documents do not match.",
                        json, MiscUtils.toJsonString(newVo), JSONCompareMode.LENIENT);

    }

    @Test(expected = EntityNotFoundException.class)
    public void delete() {
        PersonVO newVo = personService.create(EnhancedRandom.random(PersonVO.class));
        assertThat(personService.get(newVo.getId())).isNotNull();
        personService.delete(newVo.getId());
        personService.get(newVo.getId()); // entity not found - throws exception
    }

    @Test
    public void get() {
        PersonVO newVo = personService.create(EnhancedRandom.random(PersonVO.class));
        assertThat(personService.get(newVo.getId())).isNotNull();
    }

    @Test
    public void update() throws IOException, JSONException {
        String json = MiscUtils.readResourceFile("person-update.json");
        PersonVO vo = MiscUtils.toObjectFromJsonString(json,  PersonVO.class);
        log.info(vo.toString());
        PersonVO updatedVo = personService.update(1, vo);
        log.info(updatedVo.toString());
        json = MiscUtils.readResourceFile("person-update-answer.json");
        assertEquals("Values of fields that exist in both json documents do not match.",
                json, MiscUtils.toJsonString(updatedVo), JSONCompareMode.LENIENT);
    }

}