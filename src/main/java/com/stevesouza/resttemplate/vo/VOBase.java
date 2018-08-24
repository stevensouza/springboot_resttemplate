package com.stevesouza.resttemplate.vo;

import com.stevesouza.resttemplate.domain.EntityBase;
import com.stevesouza.resttemplate.utils.MiscUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.GenericTypeResolver;

// E stands for jpa Entity
@Getter
@Setter
public abstract class VOBase<E extends EntityBase> {

    public String toJson() {
        return MiscUtils.toJsonString(this);
    }

    public E toEntity() {
        return MiscUtils.convert(this, getClassOfParameterType());
    }

    private Class<E> getClassOfParameterType() {
        final int FIRST_TYPE_PARAMETER = 0;
        return  (Class<E>) GenericTypeResolver.resolveTypeArguments(getClass(), VOBase.class)[FIRST_TYPE_PARAMETER];
    }

}
