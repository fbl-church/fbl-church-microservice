package com.fbl.app.vbs.client.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * VBS Theme
 * 
 * @author Sam Butler
 * @since May 29, 2024
 */
@Getter
@Setter
@Schema(description = "The VBS Theme")
public class VBSTheme {

    @Schema(description = "VBS Theme identifier")
    private Integer id;

    @Schema(description = "The name of the VBS theme")
    @NotBlank(message = "Invalid name: Can not be empty or null")
    private String name;

    @Schema(description = "The date the vbs theme starts")
    @NotNull(message = "Invalid startDate: Can not be null")
    private LocalDate startDate;

    @Schema(description = "The date the vbs theme ends")
    @NotNull(message = "Invalid endDate: Can not be null")
    private LocalDate endDate;

    @Schema(description = "The vbs theme groups")
    private List<VBSThemeGroup> groups;

    @Schema(description = "The status of the vbs theme")
    private VBSStatus status;

    @Schema(description = "The amount of money raised")
    private Float money;

    @Schema(description = "The count of how many children registered for VBS")
    private Integer childrenRegistered;

    @Schema(description = "The count of how many children attended VBS")
    private Integer childrenAttended;

    @Schema(description = "The donation name where the money is going")
    @NotBlank(message = "Invalid donation: Can not be empty or null")
    private String donation;

    @Schema(description = "The list of vbs points for the theme")
    private List<VBSPoint> points;

    @Schema(description = "When the theme was created.")
    private LocalDateTime insertDate;
}
