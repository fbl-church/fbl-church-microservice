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
import com.fbl.app.vbs.client.domain.request.VBSGuardianChildrenGetRequest;
import com.fbl.app.vbs.dao.VBSDAO;
import com.fbl.common.page.Page;

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

    /**
     * Gets a list of guardians based of the request filter
     * 
     * @param request to filter on
     * @return list of guardian children
     */
    public Page<Child> getGuardianVbsChildren(VBSGuardianChildrenGetRequest request) {
        VBSGuardianChildrenGetRequest formatedRequest = formatSearchRequest(request);

        if (CollectionUtils.isEmpty(formatedRequest.getSearch())) {
            return Page.empty();
        }

        List<Integer> childrenIds = dao.getGuardianChildrenIds(formatedRequest);
        ChildGetRequest childRequest = new ChildGetRequest();
        childRequest.setId(childrenIds.stream().collect(Collectors.toSet()));
        return childrenService.getChildren(childRequest);
    }

    private VBSGuardianChildrenGetRequest formatSearchRequest(VBSGuardianChildrenGetRequest request) {
        if (!CollectionUtils.isEmpty(request.getSearch())) {
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
