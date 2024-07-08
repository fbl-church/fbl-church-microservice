package com.fbl.app.vbs.client.domain;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * VBS Child Points Object
 * 
 * @author Sam Butler
 * @since Jun 27, 2024
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "VBS Child Points Object")
public class VBSChildPoint {

    @Schema(description = "Child identifier")
    private Integer childId;

    @Schema(description = "VBS Attendance identifier")
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
