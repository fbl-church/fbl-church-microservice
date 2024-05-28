package com.fbl.app.vbs.client.domain;

import java.util.List;

import com.fbl.app.children.client.domain.Child;
import com.fbl.app.guardian.client.domain.Guardian;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/**
 * VBS Registration
 * 
 * @author Sam Butler
 * @since May 28, 2024
 */
@Getter
@Setter
@Schema(description = "VBS Registration Object")
public class VBSRegistration {
    @Schema(description = "List of Guardians")
    @NotEmpty(message = "Invalid children: Can not be empty or null")
    private List<Guardian> guardians;

    @Schema(description = "List of Children")
    @NotEmpty(message = "Invalid children: Can not be empty or null")
    private List<Child> children;
}
