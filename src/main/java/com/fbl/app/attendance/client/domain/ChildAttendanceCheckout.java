package com.fbl.app.attendance.client.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Class to manage child attendance checkout
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Getter
@Setter
@Schema(description = "Child Attendance object details.")
public class ChildAttendanceCheckout {
    @Schema(description = "Child identifier")
    @NotNull(message = "Invalid recordId: Must not be null")
    @Min(value = 1, message = "Invalid recordId: Must be greater than 0")
    private Integer recordId;

    @Schema(description = "First name of the child.")
    @NotNull(message = "Invalid childId: Must not be null")
    @Min(value = 1, message = "Invalid childId: Must be greater than 0")
    private Integer childId;

    @Schema(description = "The guardian id that picked them up")
    @Min(value = 1, message = "Invalid guardianId: Must be greater than 0")
    private Integer guardianId;
}
