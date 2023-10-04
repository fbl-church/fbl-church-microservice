/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.ftp.service;

import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.fbl.common.page.Page;
import com.fbl.common.util.CommonUtil;
import com.fbl.exception.types.BaseException;
import com.fbl.ftp.client.FTPStorageClient;
import com.fbl.ftp.client.domain.filters.FileSearchSpecification;
import com.fbl.ftp.client.domain.request.FileGetRequest;

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

    public void upload(MultipartFile file, String path) {
        try {
            ftpStorageClient.upload(file.getInputStream(), path, file.getOriginalFilename());
        } catch (Exception e) {
            LOGGER.error("Unable to read mulipart file: {}", file.getOriginalFilename(), e);
            throw new BaseException("Unable to read mulipart file: " + file.getOriginalFilename());
        }
    }

    /**
     * Gets a list of files in the base directory
     * 
     * @return list of files
     */
    public Page<FTPFile> getFiles(FileGetRequest request) {
        FileSearchSpecification searchFilter = new FileSearchSpecification(request.getSearch());
        return CommonUtil.listToPage(ftpStorageClient.getFiles(request.getPath(), searchFilter), request);
    }
}
