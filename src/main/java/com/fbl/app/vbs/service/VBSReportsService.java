package com.fbl.app.vbs.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.vbs.dao.VBSReportsDAO;

/**
 * VBS Report Service
 * 
 * @author Sam Butler
 * @since Jun 21, 2024
 */
@Service
public class VBSReportsService {

    @Autowired
    private VBSReportsDAO vbsReportsDao;

    /**
     * Gets statistics of children for vbs for the given theme id
     * 
     * @return a map with data
     */
    public Map<String, Integer> getCurrentlyRegisterVBSChildren() {
        Map<String, Integer> data = new HashMap<>();
        data.put("registeredChildren", vbsReportsDao.getCountOfRegisteredVBSChildren());
        return data;
    }
}
