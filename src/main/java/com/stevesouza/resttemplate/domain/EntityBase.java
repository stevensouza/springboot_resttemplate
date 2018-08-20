package com.stevesouza.resttemplate.domain;

import com.stevesouza.resttemplate.utils.MiscUtils;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.GenericTypeResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;


@Slf4j
@Getter
@Setter
@MappedSuperclass
public abstract class EntityBase<VO> {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime updatedOn;
    private String updatedBy;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // extract to methods to another class so they can be used in vo and entity

    public String toJson() {
        return MiscUtils.toJsonString(this);
    }

    public VO toVo() {
        Class<VO> clazz = getClassOfParameterType();
        return MiscUtils.convert(this, clazz);
    }

    // move to vo

    private Class getClassOfParameterType() {
        Class<VO> clazz = (Class<VO>) GenericTypeResolver.resolveTypeArgument(getClass(), EntityBase.class);
        return clazz;
    }

    // equals and hashcode were inplemented per https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntityBase)) return false;
        return id != null && id.equals(((EntityBase) o).id);
    }

    // This always gives the same hashcode which make storing in Set's inefficient, however
    // the author in the link aboves says in the size of set's typically used it is ok. The problem is
    // that you need a field that doesn't change for a hash and id is not that field as it can be null and
    // then change to a number when jpa saves it.
    @Override
    public int hashCode() {
        return 31;
    }


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
        updatedBy = updatedBy();
        updatedOn = LocalDateTime.now();
        log.info("audit info: {}, entity='{}', updatedBy='{}',  time={}", updateType, getClass().getSimpleName(), updatedBy, updatedOn);
    }

    private String updatedBy() {
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

}
