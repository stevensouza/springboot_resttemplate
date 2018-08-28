package com.stevesouza.resttemplate.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.stevesouza.resttemplate.TestUtils;
import io.github.benas.randombeans.api.EnhancedRandom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.NamingConvention;
import org.springframework.beans.BeanUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.github.benas.randombeans.api.EnhancedRandom.randomListOf;
import static org.assertj.core.api.Assertions.assertThat;
/**
 *
 */
@Slf4j
public class MiscUtilsTest {

    private static final String POJO1_JSON = "{\"firstName\":\"%s\",\"lastName\":\"%s\"}";


    @Test
    public void testReadTestResource() throws IOException {
        String fileContents = MiscUtils.readResourceFile("2lines.txt");
        assertThat(fileContents).as("Should contain '01234").contains("01234");
        assertThat(fileContents).contains("56789");
    }

    @Test(expected = RuntimeException.class)
    public void testResourceDoesntExist() throws IOException {
        String fileContents = MiscUtils.readResourceFile("i_do_not_exist.txt");
    }

    @Test
    public void convertFrom1PojoTypeToAnother() {
        Pojo1 pojo1 = MiscUtils.randomData(Pojo1.class);
        // For this to succeed both source and destination classes must have
        // getters and setters or else the model mapper must be configured to access
        // private fields.
        Pojo2 pojo2 = MiscUtils.convert(pojo1, Pojo2.class);

        assertThat(pojo2.firstName).isEqualTo(pojo1.firstName);
        assertThat(pojo2.lastName).isEqualTo(pojo1.lastName);
    }

    @Test
    public void convertFromCollectionOf1PojoTypeToAnother() {
        Pojo2List pojo2List = MiscUtils.randomData(Pojo2List.class);
        List<Pojo1> pojo1List;
        pojo1List = MiscUtils.convert(pojo2List.getPojoList(), new TypeToken<List<Pojo1>>(){});

        TestUtils.assertLenientJsonEquality(pojo1List, pojo2List.getPojoList());

        // The following test is redundant to the above, but uses the streaming/lambda jdk capabilities to create a List<Pojo1>
        // and confirm it looks like the one created from convert above.

        List<Pojo1> pojo1ListValidate = new ArrayList<>();
        for (Pojo1 pojo2 : pojo2List.getPojoList()) {
            Pojo1 pojo1 = new Pojo1(pojo2.getFirstName(), pojo2.getLastName());
            pojo1ListValidate.add(pojo1);
        }
        /**
         * stream approach for above
         *        List<Pojo1> pojo1ListValidate = pojo2List.getPojoList().stream().map(
         *                 pojo2 -> {
         *                     return new Pojo1(pojo2.getFirstName(), pojo2.getLastName());
         *                 }
         *                 ).collect(Collectors.toList());
         *         assertThat(pojo1List).isEqualTo(pojo1ListValidate);
         */
        assertThat(pojo1List).isEqualTo(pojo1ListValidate);
    }


    @Test
    public void toJsonString() {
        Pojo1 pojo1 = MiscUtils.randomData(Pojo1.class);
        String json = MiscUtils.toJsonString(pojo1);
        String expected = String.format(POJO1_JSON, pojo1.getFirstName(), pojo1.getLastName());

        assertThat(json).isEqualTo(expected);
    }

    @Test
    public void toObjectFromJsonString() {
        String json = String.format(POJO1_JSON, "joe", "jones");
        Pojo1 pojo1 = MiscUtils.toObjectFromJsonString(json, Pojo1.class);

        assertThat(pojo1.getFirstName()).isEqualTo("joe");
        assertThat(pojo1.getLastName()).isEqualTo("jones");
    }

    @Test
    public void toJsonNodeFromString()  throws IOException {
        String fileContents = MiscUtils.readResourceFile("utilstest.json");
        JsonNode json = MiscUtils.toJsonNode(fileContents);

        assertThat(json.get("firstName").asText()).isEqualTo("william");
        assertThat(json.get("lastName").asText()).isEqualTo("reid");
        assertThat(json.get("age").asInt()).isEqualTo(65);
        assertThat(json.get("phones").size()).isEqualTo(3);
        assertThat(json.get("phones").get(0).get("phoneNumber").asText()).isEqualTo("111-111-1111");
        assertThat(json.get("phones").get(1).get("phoneNumber").asText()).isEqualTo("222-222-2222");
        assertThat(json.get("phones").get(2).get("phoneNumber").asText()).isEqualTo("333-333-3333");

    }

    @Test
    public void randomPopulateList()  {
        List<Pojo1> list = MiscUtils.randomData(new ArrayList<>(), Pojo1.class);
        assertThat(list).isNotEmpty();
        assertThat(list.get(0).getClass()).isEqualTo(Pojo1.class);
    }

    @Test
    public void randomPopulateList2()  {
        List<Pojo1> list  = randomListOf(10, Pojo1.class);
        assertThat(list).hasSize(10);
        assertThat(list.get(0).getClass()).isEqualTo(Pojo1.class);
    }



    @Test
    public void randomCollectionPopulated()  {
        Pojo3 pojo = MiscUtils.randomData(Pojo3.class);
        assertThat(pojo.getSet()).isNotEmpty();
    }

    @Test
    public void randomCollectionEmpty() {
        Pojo4 pojo = MiscUtils.randomData(Pojo4.class);
        assertThat(pojo.getSet()).isEmpty();
    }

    @Test
    public void randomCollectionPopulated2() {
        Pojo4 pojo = MiscUtils.randomDataPopulateCollections(Pojo4.class);
        assertThat(pojo.getSet()).isNotEmpty();
    }


    @Test
    public void testBeanToBeanCopyWithBeanUtils() {
        Pojo1 pojo1 = EnhancedRandom.random(Pojo1.class);
        Pojo1 pojo2 = EnhancedRandom.random(Pojo1.class);

        assertThat(pojo1).isNotEqualTo(pojo2);
        BeanUtils.copyProperties(pojo1, pojo2);
        assertThat(pojo1).isEqualTo(pojo2);
    }

    @Test
    public void testBeanToBeanLenientCopy() {
        Pojo5 pojo5 = EnhancedRandom.random(Pojo5.class);
        ModelMapper modelMapper = new ModelMapper();
        // loose will allow for copying id field to techId field even though they don't match exactly.
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        Pojo6 pojo6 = modelMapper.map(pojo5, Pojo6.class);
        log.info(pojo5.toString());
        log.info(pojo6.toString());

        assertThat(pojo6.getPojo6Id()).isEqualTo(pojo5.getId());
        assertThat(pojo6.getName()).isEqualTo(pojo5.getName());
    }

    @Test
    public void testBeanToBeanLenientCopyReverse() {
        Pojo6 pojo6 = EnhancedRandom.random(Pojo6.class);
        ModelMapper modelMapper = new ModelMapper();
        // loose will allow for copying id field to techId field even though they don't match exactly.
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        Pojo5 pojo5 = modelMapper.map(pojo6, Pojo5.class);
        log.info(pojo5.toString());
        log.info(pojo6.toString());

        assertThat(pojo6.getPojo6Id()).isEqualTo(pojo5.getId());
        assertThat(pojo6.getName()).isEqualTo(pojo5.getName());
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Pojo1 {
        private String firstName;
        private String lastName;
    }

    @Data
    static class Pojo2 {
        private String firstName;
        private String lastName;
        private int age;
    }

    @Data
    static class Pojo3 {
        private String firstName;
        private Set<String> set;
    }

    @Data
    static class Pojo4 {
        private String firstName;
        private Set<String> set = new HashSet<>();
    }

    @Data
    static  class Pojo2List  {
        // note I don't know of a way to generate a list directly in random beans so using this indirect approach of putting the list in a pojo
        List<Pojo1> pojoList;
    }

    @Data
    static class Pojo5 {
        Long id;
        String name;
    }

    @Data
    static class Pojo6 {
        Long pojo6Id;
        String name;
    }
}