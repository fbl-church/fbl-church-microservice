package com.fbl.app.vbs.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.fbl.app.vbs.client.domain.VBSPoint;
import com.fbl.app.vbs.client.domain.VBSPointDivision;
import com.fbl.sql.abstracts.AbstractMapper;

/**
 * Mapper class to map a VBS Point Config {@link VBSPoint}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class VBSPointDivisionMapper extends AbstractMapper<VBSPointDivision> {
    public static VBSPointDivisionMapper VBS_POINT_DIVISION_MAPPER = new VBSPointDivisionMapper();

    public VBSPointDivision mapRow(ResultSet rs, int rowNum) throws SQLException {
        VBSPointDivision pointConfig = new VBSPointDivision();
        pointConfig.setId(rs.getInt(ID));
        pointConfig.setMin(rs.getInt(MIN));
        pointConfig.setMax(rs.getInt(MAX));
        pointConfig.setColor(rs.getString(COLOR));
        pointConfig.setVbsThemeId(rs.getInt(VBS_THEME_ID));
        pointConfig.setUpdatedUserId(rs.getInt(UPDATED_USER_ID));
        pointConfig.setInsertUserId(rs.getInt(INSERT_USER_ID));
        pointConfig.setUpdatedDate(parseDateTime(rs.getString(UPDATED_DATE)));
        pointConfig.setInsertDate(parseDateTime(rs.getString(INSERT_DATE)));
        return pointConfig;
    }
}