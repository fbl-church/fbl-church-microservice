package com.fbl.app.vbs.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbl.app.attendance.client.domain.AttendanceRecord;
import com.fbl.app.attendance.service.ManageAttendanceService;
import com.fbl.app.vbs.client.domain.VBSAttendanceRecord;
import com.fbl.app.vbs.client.domain.VBSStatus;
import com.fbl.app.vbs.client.domain.VBSTheme;
import com.fbl.app.vbs.dao.VBSAttendanceDAO;
import com.fbl.common.enums.AttendanceStatus;
import com.fbl.common.enums.ChurchGroup;
import com.fbl.exception.types.NotFoundException;
import com.fbl.exception.types.ServiceException;

/**
 * Managing VBS Attendance
 * 
 * @author Sam Butler
 * @since Jun 22, 2024
 */
@Service
public class ManageVBSAttendanceService {

    @Autowired
    private VBSAttendanceDAO vbsAttendanceDAO;

    @Autowired
    private ManageAttendanceService manageAttendanceService;

    @Autowired
    private VBSThemeService vbsThemeService;

    @Autowired
    private ManageVBSThemeService manageVBSThemeService;

    /**
     * Creates a VBS attendance record. This will be extra data that is linked to
     * the main attendance record.
     * 
     * @param vbsThemeId The vbs theme id to link the attendance too
     * @param record     The record data to add with the attendance
     */
    public VBSAttendanceRecord createVBSAttendanceRecord(int vbsThemeId, VBSAttendanceRecord record) {
        AttendanceRecord createdRecord = manageAttendanceService.createAttendanceRecord(record);
        vbsAttendanceDAO.createVBSAttendanceRecord(createdRecord.getId(), vbsThemeId, record);
        return vbsAttendanceDAO.getAttendanceRecordById(createdRecord.getId()).get();
    }

    /**
     * Creates a VBS attendance records for the given theme. Will create attendance
     * records in between and including start and end dates from the VBS Theme. Will
     * throw exception if the theme, start date, or end date is null.
     * 
     * @param vbsThemeId The vbs theme id to link the attendance too
     * @param theme      The theme to create attendance records for
     */
    public void createVBSAttendanceRecordsByTheme(int vbsThemeId, VBSTheme theme) {
        if (theme == null || theme.getStartDate() == null || theme.getEndDate() == null) {
            throw new ServiceException("Invalid Theme Given. Confirm Theme, start date, and end date are not null");
        }

        if (theme.getEndDate().isBefore(theme.getStartDate())) {
            throw new ServiceException("VBS Theme End date must be after the start date");
        }

        List<LocalDate> dates = theme.getStartDate().datesUntil(theme.getEndDate().plusDays(1)).toList();

        int dayCount = 1;
        for (LocalDate d : dates) {
            VBSAttendanceRecord vbsRec = new VBSAttendanceRecord();
            vbsRec.setName(String.format("Day %d (%s)", dayCount++, theme.getName()));
            vbsRec.setType(ChurchGroup.VBS);
            vbsRec.setActiveDate(d);
            createVBSAttendanceRecord(vbsThemeId, vbsRec);
        }
    }

    /**
     * Updates a VBS attendance record by id
     * 
     * @param id     The vbs attendance record id
     * @param record The record data to update
     */
    public void updateVBSAttendanceRecordById(int id, VBSAttendanceRecord record) {
        vbsAttendanceDAO.updateVBSAttendanceRecordById(id, record);
        manageAttendanceService.updateAttendanceRecord(id, record);
    }

    /**
     * Updates the status of an attendance record. If the status of the theme is not
     * active, then it will update the theme status to active.
     * 
     * @param id     The attendance record id
     * @param status The status to update with
     */
    public void updateAttendanceRecordStatus(int id, AttendanceStatus status) {
        if (AttendanceStatus.ACTIVE.equals(status)) {
            VBSAttendanceRecord vbsRecord = vbsAttendanceDAO.getAttendanceRecordById(id)
                    .orElseThrow(() -> new NotFoundException("VBS Attendance Record not found for id: " + id));
            VBSTheme theme = vbsThemeService.getThemeById(vbsRecord.getVbsThemeId());

            if (!VBSStatus.ACTIVE.equals(theme.getStatus())) {
                manageVBSThemeService.updateThemeStatus(theme.getId(), VBSStatus.ACTIVE);
            }
        }
        manageAttendanceService.updateAttendanceRecordStatus(id, status);
    }

    /**
     * Re-Opens a closed VBS attendance record
     * 
     * @param id The attendance record to reopen
     * @return The record that was updated
     */
    public VBSAttendanceRecord reopenTheme(int id) {
        VBSAttendanceRecord vbsRecord = vbsAttendanceDAO.getAttendanceRecordById(id)
                .orElseThrow(() -> new NotFoundException("VBS Attendance Record not found for id: " + id));
        manageVBSThemeService.reopenTheme(vbsRecord.getVbsThemeId());
        manageAttendanceService.reopenAttendanceRecord(id);
        return vbsAttendanceDAO.getAttendanceRecordById(id).get();
    }
}
