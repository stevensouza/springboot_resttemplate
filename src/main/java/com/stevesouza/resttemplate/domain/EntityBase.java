package com.stevesouza.resttemplate.domain;

import com.stevesouza.resttemplate.utils.MiscUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.TypeToken;
import org.springframework.core.GenericTypeResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;


@Slf4j
@MappedSuperclass
public abstract class EntityBase<VO> {
    @Id
    @GeneratedValue
    private long id;

    // also have: @PostPersist, @PostUpdate, @PostRemove
    @PrePersist
    public void prePersist() {
        audit("persist");
     }

    @PreUpdate
    public void preUpdate() {
        audit("update");
    }

    @PreRemove
    public void preRemove() {
        audit("remove");    }

    private void audit(String updateType) {
        log.info("audit info: {}, entity='{}', user making changes='{}',  time={}", updateType, getClass().getSimpleName(), user(), LocalDateTime.now());
    }

    private String user() {
        String userName = null;
        SecurityContextHolder.getContext();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth==null) {
            return "unknown user";
        }

        Object principal = auth.getPrincipal();
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    // extract to methods to another class so they can be used in vo and entity

    public String toJson() {
        return MiscUtils.toJsonString(this);
    }

    public VO toVo() {
        Class<VO> clazz = (Class<VO>) GenericTypeResolver.resolveTypeArgument(getClass(), EntityBase.class);
        log.info("******"+clazz);
        return MiscUtils.convert(this, clazz);
    }

    // move to vo

    public EntityBase toEntity(VO vo) {
        return MiscUtils.convert(vo, this.getClass());
    }
}
