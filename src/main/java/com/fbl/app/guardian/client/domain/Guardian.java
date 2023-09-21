/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.guardian.client.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.fbl.app.user.client.domain.User;
import com.fbl.common.enums.RelationshipType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Guardian Object class
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Getter
@Setter
@Schema(description = "Guardian object for holding guardian details for a guardian")
public class Guardian extends User {

    @Schema(description = "Guardian relation to the guardian")
    private RelationshipType relationship;

    @Schema(description = "Guardians phone number")
    @NotNull(message = "Invalid phone: Can not be null")
    @Pattern(regexp = "\\(\\d{3}\\) \\d{3}-\\d{4}", message = "Invalid phone: Format must be '(123) 456-7890")
    private String phone;

    @Schema(description = "Guardians address")
    private String address;

    @Schema(description = "Guardians city")
    private String city;

    @Schema(description = "Guardians state")
    @Length(min = 2, max = 2)
    private String state;

    @Schema(description = "Guardians zip code")
    @Length(min = 5, max = 5)
    private String zipCode;
}
