/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.attendance.client.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fbl.common.enums.ChurchGroup;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Class to create a child attendance object
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Getter
@Setter
@Schema(description = "Child Attendance object details.")
public class ChildAttendance {
    @Schema(description = "Child identifier")
    private int id;

    @Schema(description = "First name of the child.")
    @NotBlank(message = "Invalid firstName: Can not be empty or null")
    private String firstName;

    @Schema(description = "Last name of the child.")
    @NotBlank(message = "Invalid lastName: Can not be empty or null")
    private String lastName;

    @Schema(description = "The childs unique identifier")
    private String cuid;

    @Schema(description = "Any notes for the child attendance.")
    private String notes;

    @Schema(description = "The name of the record")
    private String recordName;

    @Schema(description = "The church groupd record type")
    private ChurchGroup recordType;

    @Schema(description = "The attendance record id")
    private Integer attendanceRecordId;

    @Schema(description = "The user that last updated the childs check in")
    private Integer updatedUserId;

    @Schema(description = "The date the child was checked in")
    private LocalDateTime checkInDate;

    @Schema(description = "The date the child was checked out")
    private LocalDateTime checkOutDate;

    @Schema(description = "The date the record started")
    private LocalDate recordDate;
}
