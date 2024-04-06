package com.fbl.app.attendance.service;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.fbl.app.attendance.client.domain.AttendanceRecord;
import com.fbl.app.attendance.client.domain.request.AttendanceRecordGetRequest;
import com.fbl.common.freemarker.FreeMarkerUtility;
import com.fbl.common.page.Page;
import com.fbl.common.page.domain.PageSort;

/**
 * Description
 * 
 * @author Sam Butler
 * @since Apr 05, 2024
 */
@Service
public class AttendanceScheduleService {
    @Autowired
    private FreeMarkerUtility freeMarkerUtility;

    @Autowired
    private AttendanceService attendanceService;

    /**
     * Download Attendance Record Schedule
     * 
     * @return Byte array of the schedule PDF
     */
    public byte[] downloadSchedule(AttendanceRecordGetRequest request) throws Exception {
        Page<AttendanceRecord> records = attendanceService.getAttendanceRecords(request, PageSort.ASC);
        records.forEach(r -> r.setWorkers(attendanceService.getAttendanceRecordWorkersById(r.getId())));

        String html = freeMarkerUtility.generateMessage("AttendanceSchedule.html", records);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);
        return outputStream.toByteArray();
    }
}
