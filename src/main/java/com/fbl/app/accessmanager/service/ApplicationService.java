package com.fbl.app.accessmanager.service;

import org.springframework.stereotype.Service;

import com.fbl.app.accessmanager.client.domain.Application;
import com.fbl.app.accessmanager.client.domain.request.ApplicationGetRequest;
import com.fbl.app.accessmanager.dao.ApplicationDAO;
import com.fbl.common.page.Page;

import lombok.RequiredArgsConstructor;

/**
 * Feature Access Service
 *
 * @author Sam Butler
 * @since 8/2/2020
 */
@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationDAO dao;

    /**
     * Gets a page of applications
     * 
     * @param request The request to filter applications
     * @return A page of applications
     */
    public Page<Application> getApplications(ApplicationGetRequest request) {
        return dao.getApplications(request);
    }
}
