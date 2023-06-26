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
        List<WebRole> roles = Arrays.asList(WebRole.values()).stream().filter(r -> !r.equals(WebRole.USER))
                .collect(Collectors.toList());

        if (StringUtils.hasText(request.getSearch())) {
            roles = filterPredicate(roles, request.getSearch());
        }

        int totalCount = roles.size();
        if (request.getPageSize() > 0 && totalCount > request.getPageSize()) {
            int startSlice = (int) request.getRowOffset();
            int endSlice = (int) (request.getRowOffset() + request.getPageSize());
            roles = roles.subList(startSlice, endSlice > totalCount ? totalCount : endSlice);
        }

        return new Page<>(totalCount, roles);
    }

    /**
     * Filters out base user role and will perform search on role list.
     * 
     * @param roles  The roles to fitler
     * @param search The search to filter the list on
     * @return Filtered role list
     */
    private List<WebRole> filterPredicate(List<WebRole> roles, String search) {
        return roles.stream()
                .filter(r -> r.getTextId().contains(search.toUpperCase()))
                .collect(Collectors.toList());
    }
}
