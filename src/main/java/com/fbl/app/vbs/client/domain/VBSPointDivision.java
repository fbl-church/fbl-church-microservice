package com.fbl.app.vbs.client.domain;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * VBS Point Division object
 * 
 * @author Sam Butler
 * @since Jun 20, 2024
 */
@Getter
@Setter
@Schema(description = "VBS Point Division Object")
public class VBSPointDivision {
    @Schema(description = "VBS Point Division identifier")
    private Integer id;

    @Schema(description = "The the formatted display name")
    @NotNull(message = "Invalid min: Can not null")
    @Min(value = 0, message = "Invalid min: Must be greater than or equal to 0")
    private Integer min;

    @Schema(description = "The display name of the points")
    @NotNull(message = "Invalid max: Can not null")
    @Min(value = 0, message = "Invalid max: Must be greater than or equal to 0")
    private Integer max;

    @Schema(description = "The point value")
    @NotNull(message = "Invalid color: Can not null")
    private String color;

    @Schema(description = "The id of the theme the point division are associated too")
    private Integer vbsThemeId;

    @Schema(description = "The id of the user who last updated the point division")
    private Integer updatedUserId;

    @Schema(description = "When the point division was last updated.")
    private LocalDateTime updatedDate;

    @Schema(description = "The id of the user who created the point division")
    private Integer insertUserId;

    @Schema(description = "When the point division was created.")
    private LocalDateTime insertDate;
}
