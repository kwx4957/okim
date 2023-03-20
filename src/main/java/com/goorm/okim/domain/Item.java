package com.goorm.okim.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private String title;
    @Column
    private boolean isDone;
    @Column
    private long taskId;
    @Column
    private LocalDateTime finishedAt;
}
