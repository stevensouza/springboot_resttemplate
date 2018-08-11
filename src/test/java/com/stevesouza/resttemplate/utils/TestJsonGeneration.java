package com.stevesouza.resttemplate.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Data;
import org.junit.Test;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class TestJsonGeneration {

    @Test
    public void testJacksonJsonGeneration() {
        ChildClass cc = ChildClass.builder().list(new ArrayList<>()).childName("cici").age(2).build();
        ParentClass pc = ParentClass.builder().name("steve").childClass(cc).build();

        pc.getChildClass().getList().add("moana");
        pc.getChildClass().getList().add("zootopia");
        pc.getChildClass().getList().add("george the monkey");

        JsonNode jsonNode = MiscUtils.toJsonNode(MiscUtils.toJsonString(pc));
        assertThat(jsonNode.get("name").asText()).isEqualTo(pc.getName());
        assertThat(jsonNode.get("childClass").get("childName").asText()).isEqualTo(pc.getChildClass().getChildName());
        // don't generate ignore fields
        assertThat(jsonNode.get("childClass").has("list")).
                as("'list' should not exist in the generated json").isFalse();
        assertThat(jsonNode.get("childClass").has("age")).
                as("'age' should not exist in the generated json").isFalse();

    }


    @Data
    @Builder
    public static class ParentClass {
        public String name;

        @NotNull
        @JsonIgnoreProperties(value={"list","age"})
        public ChildClass childClass;

    }

    @Data
    @Builder
    public static class ChildClass {
        @NotNull
        public String childName;
        @NotNull
        public int age;

        List<String> list;
    }

    @Data
    @Builder
    public static class ParentClassVO {
        public String name;
        public ChildClassVO childClass;
    }

    @Data
    @Builder
    public static class ChildClassVO {
        public String childName;
        public int age;
    }


}
