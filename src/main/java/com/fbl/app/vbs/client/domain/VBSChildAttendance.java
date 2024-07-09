package com.fbl.app.vbs.client.domain;

import java.util.List;

import com.fbl.app.attendance.client.domain.ChildAttendance;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * VBS Child Object
 * 
 * @author Sam Butler
 * @since Jul 09, 2024
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "VBS Child Object")
public class VBSChildAttendance extends ChildAttendance {

    @Schema(description = "The list of points for the child.")
    private List<VBSChildPoint> points;
}
