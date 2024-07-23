package com.fbl.app.vbs.client.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * VBS Child Point Cards
 * 
 * @author Sam Butler
 * @since Jul 22, 2024
 */
@Getter
@Setter
@Schema(description = "VBS Point Object")
public class VBSChildPointsCard {
    @Schema(description = "Child identifier")
    private int id;

    @Schema(description = "First name of the child.")
    private String firstName;

    @Schema(description = "Last name of the child.")
    private String lastName;

    @Schema(description = "The childs unique identifier")
    private String cuid;

    @Schema(description = "The points the child has earned")
    private int totalPoints;

    @Schema(description = "The offering points the child has earned")
    private int offeringPoints;

    @Schema(description = "The days the child has attended")
    private int daysAttended;

    @Schema(description = "The group the child is in")
    private String group;
}
