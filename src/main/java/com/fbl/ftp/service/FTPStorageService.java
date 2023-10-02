package com.fbl.ftp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.fbl.ftp.client.FTPStorageClient;

/**
 * FTP Storage Service class
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Component
public class FTPStorageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FTPStorageService.class);

    @Autowired
    private FTPStorageClient ftpStorageClient;

    public void upload(MultipartFile file) {
        try {
            ftpStorageClient.open();
            ftpStorageClient.upload(file.getInputStream(), file.getOriginalFilename());
        } catch (Exception e) {
            LOGGER.error("Unable to read mulipart file: {}", file.getName(), e);
        } finally {
            ftpStorageClient.close();
        }
    }
}
