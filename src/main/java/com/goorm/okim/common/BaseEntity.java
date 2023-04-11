package com.goorm.okim.common;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter
@MappedSuperclass
public abstract class BaseEntity {

    private ZonedDateTime createdAt;
    private ZonedDateTime lastModifiedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = ZonedDateTime.now();
        this.lastModifiedAt = ZonedDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.lastModifiedAt = ZonedDateTime.now();
    }

}
