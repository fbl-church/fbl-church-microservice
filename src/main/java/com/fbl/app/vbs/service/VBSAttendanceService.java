package com.fbl.app.vbs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.vbs.client.domain.VBSAttendanceRecord;
import com.fbl.app.vbs.dao.VBSAttendanceDAO;
import com.fbl.exception.types.NotFoundException;

/**
 * Description
 * 
 * @author Sam Butler
 * @since Jun 22, 2024
 */
@Service
public class VBSAttendanceService {

    @Autowired
    private VBSAttendanceDAO vbsAttendanceDAO;

    /**
     * Gets a page of vbs attendance records by the theme id.
     * 
     * @param themeId The theme id to get attendance records for.
     * @return Paged vbs attendance records.
     */
    public List<VBSAttendanceRecord> getVBSAttendanceRecordsByThemeId(int themeId) {
        return vbsAttendanceDAO.getVBSAttendanceRecordsByThemeId(themeId);
    }

    /**
     * Gets a vbs attendance record by id
     * 
     * @param id the id of the vbs attendance record
     * @return vbs attendance record
     */
    public VBSAttendanceRecord getAttendanceRecordById(int id) {
        return vbsAttendanceDAO.getAttendanceRecordById(id)
                .orElseThrow(() -> new NotFoundException("VBS Attendance Record not found for id: " + id));
    }
}
