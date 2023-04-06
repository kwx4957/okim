package com.goorm.okim.config;

import com.goorm.okim.jwt.CurrentUserIdResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public static final String LOCAL_FRONT = "http://localhost:8080";

    @Value("${front.url.http}")
    private String frontHttp;

    @Value("${front.url.https}")
    private String frontHttps ;

    @Autowired
    private CurrentUserIdResolver currentUserIdResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(currentUserIdResolver);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(LOCAL_FRONT, frontHttp, frontHttps)
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true);
    }
}
