/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.user.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fbl.app.user.client.domain.request.WebRoleGetRequest;
import com.fbl.common.enums.WebRole;
import com.fbl.common.page.Page;
import com.fbl.common.util.CommonUtil;
import com.fbl.jwt.utility.JwtHolder;

/**
 * Web Role Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Component
public class WebRoleService {

    private static final List<WebRole> FILTERED_ROLES = List.of(WebRole.LEADER, WebRole.WORKER, WebRole.USER,
            WebRole.GUARDIAN, WebRole.CHILD);

    @Autowired
    private JwtHolder jwtHolder;

    /**
     * Gets a list of of web roles based on the request
     * 
     * @param request to filter on
     * @return list of webroles objects
     */
    public Page<WebRole> getRoles(WebRoleGetRequest request) {
        WebRole highestUserRole = WebRole.highestRoleRank(jwtHolder.getWebRole());
        List<WebRole> roles = Arrays.asList(WebRole.values()).stream()
                .filter(r -> !FILTERED_ROLES.contains(r))
                .filter(r -> r.getRank() <= highestUserRole.getRank())
                .collect(Collectors.toList());

        return CommonUtil.enumListToPage(roles, request);
    }
}
