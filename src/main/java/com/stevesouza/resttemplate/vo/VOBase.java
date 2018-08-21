package com.stevesouza.resttemplate.vo;

import com.stevesouza.resttemplate.domain.EntityBase;
import com.stevesouza.resttemplate.utils.MiscUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.GenericTypeResolver;

// ENT stands for jpa Entity
@Getter
@Setter
public abstract class VOBase<ENT extends EntityBase> {

    public String toJson() {
        return MiscUtils.toJsonString(this);
    }

    public ENT toEntity() {
        Class<ENT> clazz = getClassOfParameterType();
        return MiscUtils.convert(this, clazz);
    }

    private Class getClassOfParameterType() {
        Class<ENT> clazz = (Class<ENT>) GenericTypeResolver.resolveTypeArgument(getClass(), VOBase.class);
        return clazz;
    }

}
