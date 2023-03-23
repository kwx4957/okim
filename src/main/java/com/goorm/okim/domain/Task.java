package com.goorm.okim.domain;

import com.goorm.okim.common.BaseEntity;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Task extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private long userId;
    @Column
    private boolean isDeleted;
    @Column
    private int progress;
}
