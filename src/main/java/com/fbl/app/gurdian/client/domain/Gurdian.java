package com.fbl.app.gurdian.client.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fbl.app.user.client.domain.User;
import com.fbl.common.enums.RelationshipType;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Gurdian Object class
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
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

    public RelationshipType getRelationship() {
        return relationship;
    }

    public void setRelationship(RelationshipType relationship) {
        this.relationship = relationship;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
