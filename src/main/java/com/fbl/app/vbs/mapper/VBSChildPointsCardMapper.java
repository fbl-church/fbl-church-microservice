package com.fbl.app.vbs.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.fbl.app.vbs.client.domain.VBSChildPointsCard;
import com.fbl.sql.abstracts.AbstractMapper;

/**
 * Mapper class to map a {@link VBSChildPointsCard}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class VBSChildPointsCardMapper extends AbstractMapper<VBSChildPointsCard> {
    public static VBSChildPointsCardMapper VBS_CHILD_POINT_CARDS_MAPPER = new VBSChildPointsCardMapper();

    public VBSChildPointsCard mapRow(ResultSet rs, int rowNum) throws SQLException {
        VBSChildPointsCard pointCard = new VBSChildPointsCard();
        pointCard.setId(rs.getInt(ID));
        pointCard.setFirstName(rs.getString(FIRST_NAME));
        pointCard.setLastName(rs.getString(LAST_NAME));
        pointCard.setCuid(rs.getString(CUID));
        pointCard.setTotalPoints(rs.getInt(POINTS));
        pointCard.setDaysAttended(rs.getInt(DAYS_ATTENDED));
        return pointCard;
    }
}