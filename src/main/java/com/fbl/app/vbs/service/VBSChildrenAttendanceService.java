package com.fbl.app.vbs.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fbl.app.attendance.client.domain.ChildAttendance;
import com.fbl.app.attendance.client.domain.request.ChildAttendanceGetRequest;
import com.fbl.app.attendance.service.ChildAttendanceService;
import com.fbl.app.children.client.domain.Child;
import com.fbl.app.children.service.ChildrenService;
import com.fbl.app.vbs.client.domain.VBSAttendanceRecord;
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
    private ChildrenService childrenService;

    @Autowired
    private VBSChildrenService vbsChildrenService;

    @Autowired
    private VBSAttendanceService vbsAttendanceService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Get a page of child attendance records and points for a given theme id and
     * child id
     * 
     * @param id      The id of the child
     * @param themeId The theme id
     * @param request The page request
     * @return The page of VBS Child Attendance
     */
    public Page<VBSChildAttendance> getVBSChildAttendance(int childId, int themeId, ChildAttendanceGetRequest request) {
        List<VBSAttendanceRecord> recs = vbsAttendanceService.getVBSAttendanceRecordsByThemeId(themeId);
        request.setAttendanceRecordId(recs.stream().map(VBSAttendanceRecord::getId).collect(Collectors.toSet()));

        Map<Integer, VBSAttendanceRecord> offeringPointsMap = buildOfferingPointsMap(recs);
        Child childData = childrenService.getChildById(childId);

        Page<ChildAttendance> cAttendance = childAttendanceService.getPageOfChildAttendanceByChildId(childId, request);
        return cAttendance.map(ca -> mapToVBSChildAttendance(childData, offeringPointsMap, ca));
    }

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
        long total = children.getTotalCount();

        Page<VBSChildAttendance> t = children.map(ca -> {
            VBSChildAttendance vbsChild = modelMapper.map(ca, VBSChildAttendance.class);
            List<VBSChildPoint> points = vbsChildrenService.getChildPointsByAttendanceId(ca.getId(), attendanceId);
            vbsChild.setPoints(points);
            return vbsChild;
        });
        t.setTotalCount(total);
        return t;
    }

    /**
     * Map the child attendance to a VBS child attendance
     * 
     * @param child  The child
     * @param vbsMap The map of id to VBSAttendanceRecord
     * @param ca     The child attendance
     * @return The VBSChildAttendance
     */
    private VBSChildAttendance mapToVBSChildAttendance(Child child, Map<Integer, VBSAttendanceRecord> vbsMap,
            ChildAttendance ca) {
        VBSChildAttendance vbsChild = modelMapper.map(ca, VBSChildAttendance.class);
        List<VBSChildPoint> points = vbsChildrenService.getChildPointsByAttendanceId(ca.getId(),
                ca.getAttendanceRecordId());

        VBSChildPoint offeringPoint = buildOfferingPoint(child, ca.getAttendanceRecordId(), vbsMap);
        if (offeringPoint != null) {
            points.add(offeringPoint);
        }

        vbsChild.setPoints(points);
        return vbsChild;
    }

    /**
     * Turns list of VBSAttendanceRecord into a map of id to offering points
     * 
     * @param recs The list of VBSAttendanceRecord
     * @return The map of id to offering points
     */
    private Map<Integer, VBSAttendanceRecord> buildOfferingPointsMap(List<VBSAttendanceRecord> recs) {
        return recs.stream().collect(Collectors.toMap(VBSAttendanceRecord::getId, r -> r));
    }

    /**
     * Build the offering child point
     * 
     * @param childId      The child id
     * @param attendanceId The attendance id
     * @param offeringMap  The offering map
     * @return The VBSChildPoint
     */
    private VBSChildPoint buildOfferingPoint(Child child, int attendanceId,
            Map<Integer, VBSAttendanceRecord> offeringMap) {
        VBSAttendanceRecord vbsAttendance = offeringMap.get(attendanceId);

        if (vbsAttendance == null || CollectionUtils.isEmpty(vbsAttendance.getOfferingWinners())) {
            return null;
        }

        if (CollectionUtils.containsAny(vbsAttendance.getOfferingWinners(), child.getChurchGroup())) {
            VBSChildPoint point = new VBSChildPoint();
            point.setChildId(child.getId());
            point.setVbsAttendanceId(attendanceId);
            point.setPoints(vbsAttendance.getOfferingWinnerPoints());
            point.setDisplayName("Offering Winner");
            point.setType("OFFERING_WINNER");
            return point;
        }

        return null;
    }
}
