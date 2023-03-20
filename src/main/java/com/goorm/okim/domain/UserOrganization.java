package com.goorm.okim.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "User_Organization")
public class UserOrganization {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private long userId;
    @Column
    private long organizationId;
}
