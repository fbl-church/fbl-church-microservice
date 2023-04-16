/**
 * Copyright of Awana App. All rights reserved.
 */
/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.configs;

import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.awana.common.page.config.PageSerializerMessageConverter;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Configuration for the custom page deserializer to be added to the message
 * converters.
 * 
 * @author Sam Butler
 * @since January 23, 2023
 */
@Configuration
public class PageSerializationConfiguration implements WebMvcConfigurer {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        ListIterator<HttpMessageConverter<?>> iterator = converters.listIterator();

        while (iterator.hasNext()) {
            HttpMessageConverter<?> converter = iterator.next();
            if (converter instanceof AbstractJackson2HttpMessageConverter) {
                iterator.previous();
                iterator.add(new PageSerializerMessageConverter(objectMapper));
                break;
            }
        }
    }
}
