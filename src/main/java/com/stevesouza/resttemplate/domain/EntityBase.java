package com.stevesouza.resttemplate.domain;

import com.stevesouza.resttemplate.utils.MiscUtils;
import com.stevesouza.resttemplate.vo.VOBase;
import lombok.Getter;
import lombok.Setter;
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
public abstract class EntityBase<VO extends VOBase, THIS_ENTITY extends EntityBase> {
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

    /**
     *
     * @return 'public class MyClass<MyVO>' would return MyVO.class
     */

    private Class<VO> getClassOfParameterType() {
        final int FIRST_TYPE_PARAMETER = 0;
        Class<VO> clazz = (Class<VO>) GenericTypeResolver.resolveTypeArguments(getClass(), EntityBase.class)[FIRST_TYPE_PARAMETER];
        return clazz;
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

    //public EntityBase update(EntityBase target) {

    public THIS_ENTITY update(THIS_ENTITY target) {
       throw new UnsupportedOperationException("This method is not implemented in: "+getClass());
    }

    // user that updated the record - for auditing purposes
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
