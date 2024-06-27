package com.fbl.app.vbs.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.fbl.app.vbs.client.domain.VBSChildPoint;
import com.fbl.sql.abstracts.AbstractMapper;

/**
 * Mapper class to map a VBS Child Point {@link VBSChildPoint}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class VBSChildrenPointsMapper extends AbstractMapper<VBSChildPoint> {
    public static VBSChildrenPointsMapper VBS_CHILD_POINTS_MAPPER = new VBSChildrenPointsMapper();

    public VBSChildPoint mapRow(ResultSet rs, int rowNum) throws SQLException {
        VBSChildPoint childPoint = new VBSChildPoint();
        childPoint.setChildId(rs.getInt(CHILD_ID));
        childPoint.setVbsAttendanceId(rs.getInt(VBS_ATTENDANCE_RECORD_ID));
        childPoint.setVbsPointId(rs.getInt(VBS_POINT_CONFIG_ID));
        childPoint.setType(rs.getString(TYPE));
        childPoint.setDisplayName(rs.getString(DISPLAY_NAME));
        childPoint.setPoints(rs.getInt(POINTS));
        childPoint.setVbsThemeId(rs.getInt(VBS_THEME_ID));
        childPoint.setInsertDate(parseDateTime(rs.getString(INSERT_DATE)));
        return childPoint;
    }
}