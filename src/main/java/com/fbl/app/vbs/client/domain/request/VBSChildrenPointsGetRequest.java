package com.fbl.app.vbs.client.domain.request;

import java.util.Set;

import com.fbl.common.page.domain.PageParam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * VBS Children Points get request object for filtering
 *
 * @author Sam Butler
 * @since September 9, 2021
 */
@Getter
@Setter
@Schema(description = "VBS Children Points get request object for filtering")
public class VBSChildrenPointsGetRequest implements PageParam {

    @Schema(description = "List of attendance ids.")
    private Set<Integer> attendanceId;

    @Schema(description = "Row Offset for pagenation.")
    private Integer rowOffset;

    @Schema(description = "Page Size for pagenation result.")
    private Integer pageSize;
}