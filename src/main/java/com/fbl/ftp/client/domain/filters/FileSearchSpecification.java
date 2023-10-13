/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.ftp.client.domain.filters;

import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.springframework.util.StringUtils;

import lombok.AllArgsConstructor;

/**
 * File Search Specification
 * 
 * @author Sam Butler
 * @since October 4, 2023
 */
@AllArgsConstructor
public class FileSearchSpecification implements FTPFileFilter {
    private final String search;

    @Override
    public boolean accept(FTPFile file) {
        if (StringUtils.hasText(search)) {
            String fileName = file.getName().toUpperCase();
            return !file.isDirectory() && fileName.contains(search.toUpperCase());
        }
        return !file.isDirectory();
    }

}
