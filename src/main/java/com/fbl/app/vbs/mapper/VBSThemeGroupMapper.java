package com.fbl.app.vbs.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.fbl.app.vbs.client.domain.VBSThemeGroup;
import com.fbl.common.enums.ChurchGroup;
import com.fbl.sql.abstracts.AbstractMapper;

/**
 * Mapper class to map a User Profile Object {@link VBSThemeGroup}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class VBSThemeGroupMapper extends AbstractMapper<VBSThemeGroup> {
    public static VBSThemeGroupMapper VBS_THEME_GROUP_MAPPER = new VBSThemeGroupMapper();

    public VBSThemeGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
        VBSThemeGroup themeGroup = new VBSThemeGroup();
        themeGroup.setVbsThemeId(rs.getInt(VBS_THEME_ID));
        themeGroup.setName(rs.getString(NAME));
        themeGroup.setGroup(ChurchGroup.valueOf(rs.getString(CHURCH_GROUP)));
        return themeGroup;
    }
}