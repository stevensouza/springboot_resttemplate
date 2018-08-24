package com.stevesouza.resttemplate.domain;

import com.stevesouza.resttemplate.vo.PhoneVO;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.Test;

public class EntityBaseTest {

    @Test(expected = UnsupportedOperationException.class)
    public void updateThrowsExceptionByDefault() {
        MyEntity entity  = EnhancedRandom.random(MyEntity.class);
        MyEntity target  = EnhancedRandom.random(MyEntity.class);
        entity.update(target);
    }


    static class MyEntity extends EntityBase<PhoneVO, MyEntity> {

    }
}