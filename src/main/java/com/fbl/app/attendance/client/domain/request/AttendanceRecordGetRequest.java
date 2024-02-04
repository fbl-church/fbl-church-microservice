/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.attendance.client.domain.request;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fbl.common.enums.AttendanceStatus;
import com.fbl.common.enums.ChurchGroup;
import com.fbl.common.page.domain.PageParam;
import com.fbl.common.search.SearchField;
import com.fbl.common.search.SearchFieldParams;
import com.fbl.common.search.SearchParam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Attendance Record filter get request.
 *
 * @author Sam Butler
 * @since September 9, 2021
 */
@Getter
@Setter
@Schema(description = "Attendance Record get request object for filtering attendance records.")
public class AttendanceRecordGetRequest
        implements PageParam, SearchParam, SearchFieldParams {

    @Schema(description = "List of attendance record ids.")
    private Set<Integer> id;

    @Schema(description = "List of attendance record names")
    private Set<String> name;

    @Schema(description = "List of attendance record status")
    private Set<AttendanceStatus> status;

    @Schema(description = "List of attendance record types")
    private Set<ChurchGroup> type;

    @Schema(description = "Search Param on search param fields.")
    private Set<String> search;

    @Schema(description = "Row Offset for pagenation.")
    private Integer rowOffset;

    @Schema(description = "Page Size for pagenation result.")
    private Integer pageSize;

    @JsonIgnore
    @Override
    public List<SearchField> getSearchFields() {
        return Arrays.asList(AttendanceRecordSearchFields.values());
    }
}