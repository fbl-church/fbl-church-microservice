package com.fbl.app.vbs.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fbl.app.vbs.openapi.TagVBS;
import com.fbl.app.vbs.service.VBSReportsService;
import com.fbl.common.annotations.interfaces.RestApiController;

/**
 * VBS Report Controller for managing endpoints
 * 
 * @author Sam Butler
 * @since June 25, 2022
 */
@RequestMapping("/api/vbs/reports")
@RestApiController
@TagVBS
public class VBSReportsController {

    @Autowired
    private VBSReportsService vbsReportsService;

    /**
     * Gets statistics of children for vbs for the given theme id
     * 
     * @param id The vbs theme id
     * @return a map with data
     */
    @GetMapping("/{id}/children")
    public Map<String, Integer> getVBSChildrenStats(@PathVariable int id) {
        return vbsReportsService.getVBSChildrenStats(id);
    }
}
