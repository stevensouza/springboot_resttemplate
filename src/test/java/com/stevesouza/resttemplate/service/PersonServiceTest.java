package com.stevesouza.resttemplate.service;

import com.stevesouza.resttemplate.TestUtils;
import com.stevesouza.resttemplate.domain.Person;
import com.stevesouza.resttemplate.domain.PersonCertification;
import com.stevesouza.resttemplate.domain.Phone;
import com.stevesouza.resttemplate.repository.PersonJpaRepository;
import com.stevesouza.resttemplate.utils.MiscUtils;
import com.stevesouza.resttemplate.vo.PersonVO;
import io.github.benas.randombeans.api.EnhancedRandom;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
public class PersonServiceTest  {
    private PersonJpaRepository personJpaRepository;
    private PersonService personService;


    @Before
    public void setUp()  {
        personJpaRepository = mock(PersonJpaRepository.class);
        personService = new PersonService(personJpaRepository);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void getAll() {
        List<Person> personList = MiscUtils.randomData(new ArrayList<>(), Person.class);
        when(personJpaRepository.findAll()).thenReturn(personList);
        List<PersonVO> personVOList = personService.getAll();
        TestUtils.assertLenientJsonEquality(personVOList, personList);
    }

    @Test
    public void post() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void get() {
    }

    @Test
    public void put() {
    }

}