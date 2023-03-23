package com.goorm.okim.jwt;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomUserPrincipal {
    private long id;
    private String sub;
}
