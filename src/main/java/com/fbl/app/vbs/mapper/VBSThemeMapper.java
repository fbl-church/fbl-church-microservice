package com.fbl.app.vbs.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.fbl.app.user.client.domain.User;
import com.fbl.app.vbs.client.domain.VBSStatus;
import com.fbl.app.vbs.client.domain.VBSTheme;
import com.fbl.sql.abstracts.AbstractMapper;

/**
 * Mapper class to map a User Profile Object {@link User}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class VBSThemeMapper extends AbstractMapper<VBSTheme> {
    public static VBSThemeMapper VBS_THEME_MAPPER = new VBSThemeMapper();

    public VBSTheme mapRow(ResultSet rs, int rowNum) throws SQLException {
        VBSTheme theme = new VBSTheme();
        theme.setId(rs.getInt(ID));
        theme.setName(rs.getString(NAME));
        theme.setStartDate(parseDate(rs.getString(START_DATE)));
        theme.setEndDate(parseDate(rs.getString(END_DATE)));
        theme.setStatus(VBSStatus.valueOf(rs.getString(STATUS)));
        theme.setChildrenRegistered(rs.getInt(CHILDREN_REGISTERED));
        theme.setDonation(rs.getString(DONATION));
        theme.setInsertDate(parseDateTime(rs.getString(INSERT_DATE)));
        return theme;
    }
}