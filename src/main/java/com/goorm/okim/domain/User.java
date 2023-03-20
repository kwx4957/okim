package com.goorm.okim.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@DynamicInsert
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private String nick;
    @Column
    private String selfDesc;
    @NotNull
    @Column
    @ColumnDefault("0")
    private boolean isWithdrawl;
    @Column
    private String githubId;

}
