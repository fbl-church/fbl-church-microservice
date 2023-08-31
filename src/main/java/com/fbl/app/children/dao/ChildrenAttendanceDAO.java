/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.children.dao;

import static com.fbl.app.children.mapper.ChildAttendanceMapper.*;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.fbl.app.children.client.domain.ChildAttendance;
import com.fbl.app.children.client.domain.request.ChildAttendanceGetRequest;
import com.fbl.common.page.Page;
import com.fbl.sql.abstracts.BaseDao;
import com.fbl.sql.builder.SqlParamBuilder;

/**
 * Class that handles all the dao calls to the database for children attendance.
 * 
 * @author Sam Butler
 * @since June 25, 2022
 */
@Repository
public class ChildrenAttendanceDAO extends BaseDao {

    public ChildrenAttendanceDAO(DataSource source) {
        super(source);
    }

    /**
     * Get a list of children attendance.
     * 
     * @param request The filters for the data.
     * @return Page of children attendance objects.
     */
    public Page<ChildAttendance> getChildrenAttedanceById(ChildAttendanceGetRequest request, int attendanceId) {
        MapSqlParameterSource params = SqlParamBuilder.with(request).useAllParams()
                .withParam(ATTENDANCE_RECORD_ID, attendanceId).withParam(PRESENT, request.getPresent()).build();

        return getPage("childrenAttendanceByIdPage", params, CHILD_ATTENDANCE_MAPPER);
    }
}
