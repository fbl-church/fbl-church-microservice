/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.app.clubber.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.awana.app.clubber.client.domain.Clubber;
import com.awana.common.enums.ChurchGroup;
import com.awana.sql.abstracts.AbstractMapper;

/**
 * Mapper class to map a Clubber Object {@link Clubber}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class ClubberMapper extends AbstractMapper<Clubber> {
	public static ClubberMapper CLUBBER_MAPPER = new ClubberMapper();

	public Clubber mapRow(ResultSet rs, int rowNum) throws SQLException {
		Clubber clubber = new Clubber();
		clubber.setId(rs.getInt(ID));
		clubber.setFirstName(rs.getString(FIRST_NAME));
		clubber.setLastName(rs.getString(LAST_NAME));
		clubber.setChurchGroup(ChurchGroup.valueOf(rs.getString(CHURCH_GROUP)));
		clubber.setBirthday(parseDate(rs.getString(BIRTHDAY)));
		clubber.setAllergies(rs.getString(ALLERGIES));
		clubber.setAdditionalInfo(rs.getString(ADDITIONAL_INFO));

		clubber.setInsertDate(rs.getTimestamp(INSERT_DATE).toLocalDateTime());
		return clubber;
	}
}
