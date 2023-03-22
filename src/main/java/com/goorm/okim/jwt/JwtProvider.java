package com.goorm.okim.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider implements InitializingBean {


    private final JwtProperty jwtProperty;
    private Key key;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.key = getSignInKey();
    }

    public Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperty.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = extractAllClaims(token);

        long id = Long.parseLong(String.valueOf(claims.get("uid")));
        String sub = (String) claims.get("sub");

        CustomUserPrincipal principal = new CustomUserPrincipal(id, sub);
        return new UsernamePasswordAuthenticationToken(principal, null, null);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public long getUserIdFromJwt(String token) {
        return Long.parseLong(String.valueOf(extractAllClaims(token).get("uid")));
    }
}
