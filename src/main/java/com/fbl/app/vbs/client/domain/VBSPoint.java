package com.fbl.app.vbs.client.domain;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

/**
 * VBS Point config object
 * 
 * @author Sam Butler
 * @since Jun 20, 2024
 */
@Getter
@Setter
@Schema(description = "VBS Point Object")
public class VBSPoint {
    @Schema(description = "VBS Point identifier")
    private Integer id;

    @Schema(description = "The the formatted display name")
    private String type;

    @Schema(description = "The display name of the points")
    @NotBlank(message = "Invalid displayName: Can not be empty or null")
    private String displayName;

    @Schema(description = "The point value")
    @NotNull(message = "Invalid points: Can not null")
    @Positive(message = "Invalid points: Must be greater than 0")
    private Integer points;

    @Schema(description = "Determines if the points are only for registration.")
    private boolean registrationOnly;

    @Schema(description = "Determines if the points should auto apply on check in")
    private boolean checkInApply;

    @Schema(description = "If the points are enabled")
    private boolean enabled;

    @Schema(description = "The id of the theme the points are associated too")
    private Integer vbsThemeId;

    @Schema(description = "The id of the user who last updated the point config")
    private Integer updatedUserId;

    @Schema(description = "When the point config was last updated.")
    private LocalDateTime updatedDate;

    @Schema(description = "The id of the user who created the point config")
    private Integer insertUserId;

    @Schema(description = "When the point config was created.")
    private LocalDateTime insertDate;
}
