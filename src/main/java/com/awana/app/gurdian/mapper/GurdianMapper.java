/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.app.gurdian.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.awana.app.gurdian.client.domain.Gurdian;
import com.awana.sql.abstracts.AbstractMapper;

/**
 * Mapper class to map a Gurdian Object {@link Gurdian}
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class GurdianMapper extends AbstractMapper<Gurdian> {
	public static GurdianMapper GURDIAN_MAPPER = new GurdianMapper();

	public Gurdian mapRow(ResultSet rs, int rowNum) throws SQLException {
		Gurdian gurdian = new Gurdian();

		return gurdian;
	}
}
