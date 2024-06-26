/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.children.client.domain;

import java.time.LocalDate;
import java.util.List;

import com.fbl.app.guardian.client.domain.Guardian;
import com.fbl.app.user.client.domain.User;
import com.fbl.common.enums.ChurchGroup;
import com.fbl.common.enums.RelationshipType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Class to create a child object
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Getter
@Setter
@Schema(description = "Child object for holding child details")
public class Child extends User {
    @Schema(description = "The childs unique identifier")
    private String cuid;

    @Schema(description = "Child relation to the guardian")
    private RelationshipType relationship;

    @Schema(description = "The user church group")
    private List<ChurchGroup> churchGroup;

    @Schema(description = "Childs Allergies, can be null")
    private List<String> allergies;

    @Schema(description = "Childs Birthday")
    private LocalDate birthday;

    @Schema(description = "Any additional information about the child")
    private String additionalInfo;

    @Schema(description = "Childs guardians")
    private List<Guardian> guardians;

    @Schema(description = "Childs release of liability agreement")
    private boolean releaseOfLiability;
}
