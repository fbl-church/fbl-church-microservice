/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@ServletComponentScan
@EnableScheduling
@EnableConfigurationProperties
public class InsiteMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsiteMicroserviceApplication.class, args);
	}
}
