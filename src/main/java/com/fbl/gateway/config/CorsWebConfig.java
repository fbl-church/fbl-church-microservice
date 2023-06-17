/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS Configuration to setting allowed origins and methods on the response
 * body.
 * 
 * @author Sam Butler
 * @since July 29, 2022
 */
@Configuration
public class CorsWebConfig {

    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedHeaders(HttpHeaders.AUTHORIZATION, HttpHeaders.CACHE_CONTROL, HttpHeaders.CONTENT_TYPE)
                        .allowedMethods("GET", "POST", "PUT", "DELETE").exposedHeaders("Total-Count");
            }
        };
    }
}
