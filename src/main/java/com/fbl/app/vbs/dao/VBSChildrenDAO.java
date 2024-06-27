package com.fbl.app.vbs.dao;

import static com.fbl.app.vbs.mapper.VBSChildrenPointsMapper.*;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.fbl.app.vbs.client.domain.VBSChildPoint;
import com.fbl.app.vbs.client.domain.request.VBSChildrenPointsGetRequest;
import com.fbl.common.page.Page;
import com.fbl.sql.abstracts.BaseDao;
import com.fbl.sql.builder.SqlParamBuilder;

/**
 * Description
 * 
 * @author Sam Butler
 * @since Jun 27, 2024
 */
@Repository
public class VBSChildrenDAO extends BaseDao {

    public VBSChildrenDAO(DataSource source) {
        super(source);
    }

    /**
     * Get child points by the children points get request
     * 
     * @param childId The child id
     * @param request The children points get request
     * @return The child points
     */
    public Page<VBSChildPoint> getChildPoints(int childId, VBSChildrenPointsGetRequest request) {
        MapSqlParameterSource params = SqlParamBuilder.with(request)
                .withParam(VBS_ATTENDANCE_RECORD_ID, request.getAttendanceId())
                .withParam(CHILD_ID, childId).build();
        return getPage("getChildPointsPage", params, VBS_CHILD_POINTS_MAPPER);
    }

    /**
     * Add points to a child
     * 
     * @param points The points to add to the child
     * @return The child points
     */
    public void addPointsToChild(int childId, int vbsAttendanceId, int pointId) {
        MapSqlParameterSource params = SqlParamBuilder.with()
                .withParam(CHILD_ID, childId)
                .withParam(VBS_ATTENDANCE_RECORD_ID, vbsAttendanceId)
                .withParam(VBS_POINT_CONFIG_ID, pointId)
                .build();

        post("addPointsToChild", params);
    }
}
