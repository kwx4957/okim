package com.goorm.okim.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "user_organization")
public class UserOrganization {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;
}
