package com.fbl.app.vbs.client.domain;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

/**
 * VBS Child Points Object
 * 
 * @author Sam Butler
 * @since Jun 27, 2024
 */
@Getter
@Setter
@Schema(description = "VBS Child Points Object")
public class VBSChildPoint {

    @Schema(description = "Child identifier")
    @NotNull(message = "Invalid childId: Can not null")
    @Positive(message = "Invalid childId: Must be greater than 0")
    private Integer childId;

    @Schema(description = "VBS Attendance identifier")
    @NotNull(message = "Invalid vbsAttendanceId: Can not null")
    @Positive(message = "Invalid vbsAttendanceId: Must be greater than 0")
    private Integer vbsAttendanceId;

    @Schema(description = "VBS Point identifier")
    @NotNull(message = "Invalid vbsPointId: Can not null")
    @Positive(message = "Invalid vbsPointId: Must be greater than 0")
    private Integer vbsPointId;

    @Schema(description = "The the formatted display name")
    private String type;

    @Schema(description = "The display name of the points")
    private String displayName;

    @Schema(description = "The point value")
    private Integer points;

    @Schema(description = "The id of the theme the points are associated too")
    private Integer vbsThemeId;

    @Schema(description = "When the point config was created.")
    private LocalDateTime insertDate;
}
