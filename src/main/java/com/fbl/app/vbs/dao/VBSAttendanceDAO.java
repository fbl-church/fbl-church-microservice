package com.fbl.app.vbs.dao;

import static com.fbl.app.vbs.mapper.VBSAttendanceRecordMapper.*;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.fbl.app.vbs.client.domain.VBSAttendanceRecord;
import com.fbl.common.util.CommonUtil;
import com.fbl.sql.abstracts.BaseDao;
import com.fbl.sql.builder.SqlParamBuilder;

/**
 * VBS Attendance DAO for making calls to the database
 * 
 * @author Sam Butler
 * @since Jun 22, 2024
 */
@Repository
public class VBSAttendanceDAO extends BaseDao {

    public VBSAttendanceDAO(DataSource source) {
        super(source);
    }

    /**
     * Gets a list of vbs attendance records by the theme id.
     * 
     * @param themeId The theme id to get attendance records for.
     * @return list vbs attendance records.
     */
    public List<VBSAttendanceRecord> getVBSAttendanceRecordsByThemeId(int themeId) {
        return getList("getVBSAttendanceRecordsPage", parameterSource(VBS_THEME_ID, themeId),
                VBS_ATTENDANCE_RECORD_MAPPER);
    }

    /**
     * Gets a vbs attendance record by id
     * 
     * @param id the id of the vbs attendance record
     * @return vbs attendance record
     */
    public Optional<VBSAttendanceRecord> getAttendanceRecordById(int id) {
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(ID, id).build();
        return getOptional("getAttendanceRecordById", params, VBS_ATTENDANCE_RECORD_MAPPER);
    }

    /**
     * Creates a VBS attendance record. This will be extra data that is linked to
     * the main attendance record.
     * 
     * @param attendanceRecordId The record id of an already existing attendance
     * @param vbsThemeId         The vbs theme id to link the attendance too
     * @param record             The record data to add with the attendance
     */
    public void createVBSAttendanceRecord(int attendanceRecordId, int vbsThemeId, VBSAttendanceRecord record) {
        MapSqlParameterSource params = SqlParamBuilder.with()
                .withParam(ATTENDANCE_RECORD_ID, attendanceRecordId)
                .withParam(VBS_THEME_ID, vbsThemeId)
                .withParam(MONEY, record.getMoney() == null ? 0.00 : record.getMoney())
                .withParam(SPIRIT_THEME, record.getSpiritTheme())
                .withParam(OFFERING_WINNERS, CommonUtil.serializeEnumList(record.getOfferingWinners(), ","))
                .build();

        post("createVBSAttendanceRecord", params);
    }

    /**
     * Updates a VBS attendance record by id
     * 
     * @param id     The vbs attendance record id
     * @param record The record data to update
     */
    public void updateVBSAttendanceRecordById(int id, VBSAttendanceRecord record) {
        MapSqlParameterSource params = SqlParamBuilder.with()
                .withParam(ATTENDANCE_RECORD_ID, id)
                .withParam(MONEY, record.getMoney() == null ? 0.00 : record.getMoney())
                .withParam(SPIRIT_THEME, record.getSpiritTheme())
                .withParam(OFFERING_WINNERS, CommonUtil.serializeEnumList(record.getOfferingWinners(), ","))
                .withParam(OFFERING_WINNER_POINTS, record.getOfferingWinnerPoints())
                .build();

        update("updateVBSAttendanceRecordById", params);
    }
}
