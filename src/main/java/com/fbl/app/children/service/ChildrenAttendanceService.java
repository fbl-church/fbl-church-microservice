package com.fbl.app.children.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.children.client.domain.ChildAttendance;
import com.fbl.app.children.client.domain.request.ChildAttendanceGetRequest;
import com.fbl.app.children.dao.ChildrenAttendanceDAO;
import com.fbl.common.page.Page;

/**
 * Children Attendance Service class that handles all service calls to the dao
 * 
 * @author Sam Butler
 * @since June 25, 2022
 */
@Service
public class ChildrenAttendanceService {

    @Autowired
    private ChildrenAttendanceDAO childrenAttendanceDAO;

    /**
     * Get a list of children attendance.
     * 
     * @param request The filters for the data.
     * @return Page of children attendance objects.
     */
    public Page<ChildAttendance> getChildrenAttedanceById(ChildAttendanceGetRequest request, int attendanceId) {
        return childrenAttendanceDAO.getChildrenAttedanceById(request, attendanceId);
    }
}
