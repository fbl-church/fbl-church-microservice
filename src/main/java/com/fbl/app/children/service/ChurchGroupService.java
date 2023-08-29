/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.children.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fbl.app.children.client.domain.request.ChurchGroupGetRequest;
import com.fbl.common.enums.ChurchGroup;
import com.fbl.common.page.Page;
import com.fbl.common.util.CommonUtil;

/**
 * Church Group Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Component
public class ChurchGroupService {

    /**
     * Gets a list of church groups based on the request
     * 
     * @param request to filter on
     * @return list of church group objects
     */
    public Page<ChurchGroup> getChurchGroups(ChurchGroupGetRequest request) {
        List<ChurchGroup> groups = Arrays.asList(ChurchGroup.values()).stream()
                .collect(Collectors.toList());
        return CommonUtil.enumListToPage(groups, request);
    }
}