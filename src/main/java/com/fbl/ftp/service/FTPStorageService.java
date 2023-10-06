/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.ftp.service;

import java.io.InputStream;

import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
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

    @Autowired
    private FTPStorageClient ftpStorageClient;

    /**
     * Gets a list of files in the base directory
     * 
     * @return list of files
     */
    public Page<FTPFile> getFiles(FileGetRequest request) {
        FileSearchSpecification searchFilter = new FileSearchSpecification(request.getSearch());
        return CommonUtil.listToPage(ftpStorageClient.get(request.getPath(), searchFilter), request);
    }

    /**
     * Download a file
     * 
     * @return The file input stream
     */
    public InputStreamResource downloadFile(String filePath) {
        InputStream is = ftpStorageClient.download(filePath);
        if(is == null) {
            return null;
        }
        return new InputStreamResource(is);
    }

    /**
     * Upload the given file to the provided path.
     * 
     * @param file The file to be uploaded
     * @param path Where to store the file.
     */
    public void upload(MultipartFile file, String path) {
        try {
            ftpStorageClient.upload(file.getInputStream(), path, file.getOriginalFilename());
        } catch (Exception e) {
            throw new BaseException("Unable to upload mulipart file: " + file.getOriginalFilename());
        }
    }

    /**
     * Delete the given file
     * 
     * @param file The file to delete
     */
    public void deleteFile(String file) {
        ftpStorageClient.deleteFile(file);
    }
}
