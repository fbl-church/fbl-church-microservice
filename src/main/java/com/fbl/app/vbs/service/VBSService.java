/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.vbs.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.fbl.app.children.client.domain.Child;
import com.fbl.app.children.client.domain.request.ChildGetRequest;
import com.fbl.app.children.service.ChildrenService;
import com.fbl.app.guardian.client.domain.Guardian;
import com.fbl.app.guardian.client.domain.request.GuardianGetRequest;
import com.fbl.app.guardian.service.GuardianService;
import com.fbl.app.vbs.dao.VBSDAO;
import com.fbl.common.page.Page;
import com.fbl.common.search.SearchParam;

/**
 * VBS Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2022
 */
@Service
public class VBSService {

    @Autowired
    private VBSDAO dao;

    @Autowired
    private ChildrenService childrenService;

    @Autowired
    private GuardianService guardianService;

    /**
     * Gets a list of vbs guardians based of the request filter
     * 
     * @param request to filter on
     * @return page of guardian children
     */
    public Page<Guardian> getGuardians(GuardianGetRequest request) {
        formatSearchRequest(request);
        if (request == null || CollectionUtils.isEmpty(request.getSearch())) {
            return Page.empty();
        }

        request.setEmail(request.getSearch());
        request.setPhone(request.getSearch());
        request.setSearch(null);
        return dao.getGuardians(request);
    }

    /**
     * Gets a list of guardians based of the request filter
     * 
     * @param request to filter on
     * @return list of guardian children
     */
    public Page<Child> getGuardianVbsChildren(int id) {
        return Page.of(childrenService.getGuardianChildren(id));
    }

    /**
     * Gets a a vbs child by id
     * 
     * @param request The request for children
     * @return page of guardian children
     */
    public List<Child> getVbsChildren(ChildGetRequest request) {
        Page<Child> children = childrenService.getChildren(request);
        children.forEach(c -> c.setGuardians(guardianService.getChildGuardians(c.getId())));
        return children.getList();
    }

    /**
     * Formats the given search param request
     * 
     * @param <T>     The generic request that extends SearchParam
     * @param request The request to format
     * @return The formatted search request
     */
    private <T extends SearchParam> T formatSearchRequest(T request) {
        if (request != null && !CollectionUtils.isEmpty(request.getSearch())) {
            Set<String> filteredSearch = request.getSearch().stream().map(s -> s.contains("@") ? s : formatPhone(s))
                    .filter(s -> StringUtils.hasText(s))
                    .collect(Collectors.toSet());

            request.setSearch(filteredSearch);
        }

        return request;
    }

    private String formatPhone(String phoneString) {
        String phoneFiltered = phoneString.replaceAll("\\D+", "");
        if (phoneFiltered.length() != 10) {
            return null;
        }

        return String.format("(%s) %s-%s", phoneFiltered.substring(0, 3), phoneFiltered.substring(3, 6),
                phoneFiltered.substring(6, 10));
    }

}
