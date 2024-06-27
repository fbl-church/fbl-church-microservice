package com.fbl.app.vbs.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.fbl.app.vbs.client.domain.VBSPoint;
import com.fbl.sql.abstracts.AbstractMapper;

/**
 * Mapper class to map a VBS Point Config {@link VBSPoint}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class VBSPointsMapper extends AbstractMapper<VBSPoint> {
    public static VBSPointsMapper VBS_POINTS_MAPPER = new VBSPointsMapper();

    public VBSPoint mapRow(ResultSet rs, int rowNum) throws SQLException {
        VBSPoint pointConfig = new VBSPoint();
        pointConfig.setId(rs.getInt(ID));
        pointConfig.setType(rs.getString(TYPE));
        pointConfig.setDisplayName(rs.getString(DISPLAY_NAME));
        pointConfig.setPoints(rs.getInt(POINTS));
        pointConfig.setRegistrationOnly(rs.getBoolean(REGISTRATION_ONLY));
        pointConfig.setVbsThemeId(rs.getInt(VBS_THEME_ID));
        pointConfig.setUpdatedUserId(rs.getInt(UPDATED_USER_ID));
        pointConfig.setInsertUserId(rs.getInt(INSERT_USER_ID));
        pointConfig.setUpdatedDate(parseDateTime(rs.getString(UPDATED_DATE)));
        pointConfig.setInsertDate(parseDateTime(rs.getString(INSERT_DATE)));
        return pointConfig;
    }
}