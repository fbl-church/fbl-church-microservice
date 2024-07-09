package com.fbl.app.vbs.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.attendance.client.domain.ChildAttendance;
import com.fbl.app.attendance.client.domain.request.ChildAttendanceGetRequest;
import com.fbl.app.attendance.service.ChildAttendanceService;
import com.fbl.app.vbs.client.domain.VBSChildAttendance;
import com.fbl.app.vbs.client.domain.VBSChildPoint;
import com.fbl.common.page.Page;

/**
 * VBS Children Attendance Service
 * 
 * @author Sam Butler
 * @since Jul 09, 2024
 */
@Service
public class VBSChildrenAttendanceService {

    @Autowired
    private ChildAttendanceService childAttendanceService;

    @Autowired
    private VBSChildrenService vbsChildrenService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Get a page of VBS children by attendance id
     * 
     * @param attendanceId The attendance id to get the children for
     * @param request      The page request
     * @return The page of VBS children
     */
    public Page<VBSChildAttendance> getVBSChildrenByAttendanceId(int attendanceId,
            ChildAttendanceGetRequest request) {
        Page<ChildAttendance> children = childAttendanceService.getChildrenAttendanceById(attendanceId, request);

        return children.map(ca -> {
            VBSChildAttendance vbsChild = modelMapper.map(ca, VBSChildAttendance.class);
            List<VBSChildPoint> points = vbsChildrenService.getChildPointsByAttendanceId(ca.getId(), attendanceId);
            vbsChild.setPoints(points);
            return vbsChild;
        });
    }
}
