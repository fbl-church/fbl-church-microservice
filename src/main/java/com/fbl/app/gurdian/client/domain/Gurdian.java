package com.fbl.app.gurdian.client.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.fbl.app.user.client.domain.User;
import com.fbl.common.enums.RelationshipType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Gurdian Object class
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Getter
@Setter
@Schema(description = "Gurdian object for holding gurdian details for a gurdian")
public class Gurdian extends User {

    @Schema(description = "Gurdian relation to the gurdian")
    private RelationshipType relationship;

    @Schema(description = "Gurdians phone number")
    @NotNull(message = "Invalid phone: Can not be null")
    @Pattern(regexp = "\\(\\d{3}\\) \\d{3}-\\d{4}", message = "Invalid phone: Format must be '(123) 456-7890")
    private String phone;

    @Schema(description = "Gurdians address")
    private String address;

    @Schema(description = "Gurdians city")
    @NotBlank
    private String city;

    @Schema(description = "Gurdians state")
    @Length(min = 2, max = 2)
    private String state;

    @Schema(description = "Gurdians zip code")
    @Length(min = 5, max = 5)
    private String zipCode;
}
