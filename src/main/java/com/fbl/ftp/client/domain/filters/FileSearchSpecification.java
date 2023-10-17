/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.ftp.client.domain.filters;

import java.util.Set;
import java.util.stream.Collectors;

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
    private final Set<String> search;

    @Override
    public boolean accept(FTPFile file) {
        if (search == null) {
            return true;
        }

        Set<String> filteredSearch = search.stream().filter(StringUtils::hasText).collect(Collectors.toSet());
        if (filteredSearch.size() > 0) {
            String fileName = file.getName().toUpperCase();
            boolean found = filteredSearch.stream().filter(s -> fileName.contains(s.toUpperCase())).findAny()
                    .isPresent();
            return !file.isDirectory() && found;
        }
        return !file.isDirectory();
    }

}
