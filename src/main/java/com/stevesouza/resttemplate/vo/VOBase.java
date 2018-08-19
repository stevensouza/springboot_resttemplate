package com.stevesouza.resttemplate.vo;

import com.stevesouza.resttemplate.domain.EntityBase;
import com.stevesouza.resttemplate.utils.MiscUtils;
import org.springframework.core.GenericTypeResolver;

// E stands for Entity
public abstract class VOBase<E> {

    public String toJson() {
        return MiscUtils.toJsonString(this);
    }

    public E toEntity() {
        Class<E> clazz = getClassOfParameterType();
        return MiscUtils.convert(this, clazz);
    }

    private Class getClassOfParameterType() {
        Class<E> clazz = (Class<E>) GenericTypeResolver.resolveTypeArgument(getClass(), VOBase.class);
        return clazz;
    }

}
