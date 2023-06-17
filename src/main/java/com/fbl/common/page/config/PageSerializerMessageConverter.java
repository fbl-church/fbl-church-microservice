/**
 * Copyright of FBL Church App. All rights reserved.
 */
/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.common.page.config;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fbl.common.page.Page;

/**
 * Custom serializer for the page object when called from a controller class.
 * 
 * @author Sam Butler
 * @since January 23, 2023
 */
public class PageSerializerMessageConverter extends MappingJackson2HttpMessageConverter {
    /**
     * Page Serializer Http Message Converter
     * 
     * @param objectMapper Instance of Object Mapper to use for serialization.
     */
    public PageSerializerMessageConverter(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return super.canWrite(clazz, mediaType) && clazz.isAssignableFrom(Page.class);
    }

    @Override
    public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
        return canWrite(clazz, mediaType);
    }

    @Override
    protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException {
        if(object instanceof Page) {
            Page<?> page = (Page<?>) object;
            HttpHeaders headers = outputMessage.getHeaders();
            headers.remove(Page.TOTAL_ITEM_COUNT);
            headers.put(Page.TOTAL_ITEM_COUNT, Arrays.asList(String.valueOf(page.getTotalCount())));
            super.writeInternal(page.getList(), outputMessage);
        }else {
            super.writeInternal(object, type, outputMessage);
        }
    }
}