package com.fbl.app.vbs.client.domain;

import com.fbl.common.enums.ChurchGroup;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * VBS Theme Group
 * 
 * @author Sam Butler
 * @since Jun 21, 2024
 */
@Getter
@Setter
@Schema(description = "VBS Theme Group Object")
public class VBSThemeGroup {
    @Schema(description = "VBS Theme identifier")
    private Integer vbsThemeId;

    @Schema(description = "The church group")
    @NotNull(message = "Invalid group: Can not be null")
    private ChurchGroup group;

    @Schema(description = "VBS Theme specific name for the group")
    @NotBlank(message = "Invalid name: Can not be empty or null")
    private String name;
}
