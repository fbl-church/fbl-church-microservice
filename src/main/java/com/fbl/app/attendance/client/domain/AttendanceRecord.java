/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.attendance.client.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fbl.app.user.client.domain.User;
import com.fbl.common.enums.AttendanceStatus;
import com.fbl.common.enums.ChurchGroup;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Attendance Record data object
 * 
 * @author Sam Butler
 * @since August 21, 2023
 */
@Getter
@Setter
@Schema(description = "Attendance Record object for holding attendance details.")
public class AttendanceRecord {

    @Schema(description = "Attendance Record identifier.")
    private int id;

    @Schema(description = "Name describing the attendance record.")
    @NotNull(message = "Invalid name: Can not be null")
    private String name;

    @Schema(description = "Status of the attendance record.")
    private AttendanceStatus status;

    @Schema(description = "The record type")
    @NotNull(message = "Invalid type: Can not be null")
    private ChurchGroup type;

    @Schema(description = "The unit session details")
    private String unitSession;

    @Schema(description = "The workers on the attendance record.")
    private List<User> workers;

    @Schema(description = "The date the attendance record was active.")
    @NotNull(message = "Invalid activeDate: Can not be null")
    private LocalDate activeDate;

    @Schema(description = "Who started the attendance record")
    private Integer startedByUserId;

    @Schema(description = "Who closed the attendance record")
    private Integer closedByUserId;

    @Schema(description = "The date time the attendance record was closed.")
    private LocalDateTime closedDate;

    @Schema(description = "When the attendance record was created.")
    private LocalDateTime insertDate;
}
