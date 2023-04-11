package com.goorm.okim.config;

import com.goorm.okim.filter.GlobalRequestFilter;
import com.goorm.okim.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter authenticationFilter;
    private final GlobalRequestFilter globalRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .addFilterBefore(globalRequestFilter, UsernamePasswordAuthenticationFilter.class)
                    .authorizeHttpRequests()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/user/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/user/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/v1/task/**").authenticated()
                        .anyRequest().permitAll()
                .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
