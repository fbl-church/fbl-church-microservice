package com.awana.app.gurdian.client.domain;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.awana.common.enums.RelationshipType;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Gurdian Object class
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Schema(description = "Gurdian object for holding gurdian details for a gurdian")
public class Gurdian {

    @Schema(description = "Gurdian id the gurdian belongs too")
    private int id;

    @Schema(description = "First name of the gurdian")
    @NotBlank(message = "Invalid firstName: Can not be empty or null")
    private String firstName;

    @Schema(description = "Last name of the gurdian")
    @NotBlank(message = "Invalid lastName: Can not be empty or null")
    private String lastName;

    @Schema(description = "Gurdians email")
    private String email;

    @Schema(description = "Gurdian relation to the gurdian")
    @NotNull(message = "Invalid relationship: Can not be null")
    private RelationshipType relationship;

    @Schema(description = "Gurdians phone number")
    @NotNull(message = "Invalid phone: Can not be null")
    @Size(min = 10, max = 10, message = "Invalid phone: Length must be 10 digits long")
    private String phone;

    @Schema(description = "Gurdians address")
    private String address;

    @Schema(description = "When the gurdian was created.")
    private LocalDateTime insertDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public LocalDateTime getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(LocalDateTime insertDate) {
        this.insertDate = insertDate;
    }
}
