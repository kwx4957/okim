package com.goorm.okim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OkimApplication {

	public static void main(String[] args) {
		SpringApplication.run(OkimApplication.class, args);
	}

}
