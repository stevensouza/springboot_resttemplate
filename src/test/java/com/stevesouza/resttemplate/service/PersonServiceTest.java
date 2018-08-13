package com.stevesouza.resttemplate.service;

import com.stevesouza.resttemplate.TestUtils;
import com.stevesouza.resttemplate.db.Person;
import com.stevesouza.resttemplate.db.PersonJpaRepository;
import com.stevesouza.resttemplate.utils.MiscUtils;
import com.stevesouza.resttemplate.vo.PersonVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
public class PersonServiceTest  {
    private PersonJpaRepository personJpaRepository;
    private PersonService personService;


    @Before
    public void setUp() throws Exception {
        personJpaRepository = mock(PersonJpaRepository.class);
        personService = new PersonService(personJpaRepository);
    }

    @After
    public void tearDown() throws Exception {

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