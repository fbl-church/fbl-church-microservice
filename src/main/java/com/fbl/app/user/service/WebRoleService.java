package com.fbl.app.user.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fbl.app.user.client.domain.request.WebRoleGetRequest;
import com.fbl.common.enums.WebRole;
import com.fbl.common.page.Page;

/**
 * Web Role Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Component
public class WebRoleService {

    /**
     * Gets a list of of web roles based on the request
     * 
     * @param request to filter on
     * @return list of webroles objects
     */
    public Page<WebRole> getRoles(WebRoleGetRequest request) {
        List<WebRole> roles = Arrays.asList(WebRole.values());

        if (StringUtils.hasText(request.getSearch())) {
            roles = roles.stream().filter(r -> r.getTextId().contains(request.getSearch().toUpperCase()))
                    .collect(Collectors.toList());
        }

        int totalCount = roles.size();
        if (request.getPageSize() > 0 && roles.size() > request.getPageSize()) {
            int startSlice = (int) request.getRowOffset();
            int endSlice = (int) (request.getRowOffset() + request.getPageSize());
            roles = roles.subList(startSlice, endSlice);
        }

        return new Page<>(totalCount, roles);
    }
}
