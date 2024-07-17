package com.fbl.app.vbs.client.domain;

import java.util.List;

import com.fbl.app.attendance.client.domain.AttendanceRecord;
import com.fbl.common.enums.ChurchGroup;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * VBS Attendance Record object
 * 
 * @author Sam Butler
 * @since Jun 22, 2024
 */
@Getter
@Setter
@Schema(description = "VBS Attendance Record Object")
public class VBSAttendanceRecord extends AttendanceRecord {
    @Schema(description = "The id of the theme the points are associated too")
    private Integer vbsThemeId;

    @Schema(description = "The amount of money raised")
    private Float money;

    @Schema(description = "The spirit theme for the attendance record")
    private String spiritTheme;

    @Schema(description = "The winners of the offering")
    private List<ChurchGroup> offeringWinners;

    @Schema(description = "The point value for the offering winners")
    private int offeringWinnerPoints;
}
