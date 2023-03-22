package com.goorm.okim.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter @Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperty {
    private String secretKey;
    private long accessTokenExp;
    private long refreshTokenExp;
}