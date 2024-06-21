package com.fbl.app.vbs.client.domain;

import com.fbl.common.enums.ChurchGroup;

import io.swagger.v3.oas.annotations.media.Schema;
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
    private ChurchGroup group;

    @Schema(description = "VBS Theme specific name for the group")
    private String name;
}
