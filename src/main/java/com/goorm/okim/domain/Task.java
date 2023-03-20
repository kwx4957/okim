package com.goorm.okim.domain;

import jakarta.persistence.*;

@Entity
public class Task {
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
