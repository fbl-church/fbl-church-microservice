/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.fbl.environment.EnvironmentService;

@SpringBootApplication(exclude = { UserDetailsServiceAutoConfiguration.class })
@ServletComponentScan
@EnableScheduling
public class FBLChurchApplication {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", EnvironmentService.APP_CONFIG_NAME);
		SpringApplication.run(FBLChurchApplication.class, args);
	}
}
