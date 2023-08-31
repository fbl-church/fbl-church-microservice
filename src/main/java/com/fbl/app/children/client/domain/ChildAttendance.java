/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.children.client.domain;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "If the child was present for the attendance")
    private boolean present;

    @Schema(description = "The attendance record id")
    private int attendanceRecordId;

    @Schema(description = "The attendance notes")
    private String notes;

    @Schema(description = "The user that last updated the childs check in")
    private int updatedUserId;

    @Schema(description = "The date the child was checked in")
    private LocalDateTime checkInDate;
}
