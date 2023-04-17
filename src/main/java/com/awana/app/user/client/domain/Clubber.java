/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.app.user.client.domain;

import java.time.LocalDate;

import com.awana.common.enums.ChurchGroup;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Class to create a clubber object
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Schema(description = "Clubber object for holding clubber details")
public class Clubber {

    @Schema(description = "Clubber identifier")
    private int id;

    @Schema(description = "First name of the clubber")
    private String firstName;

    @Schema(description = "Last name of the clubber")
    private String lastName;

    @Schema(description = "The user church group")
    private ChurchGroup group;

    @Schema(description = "Clubbers Allergies, can be null")
    private String allergies;

    @Schema(description = "Clubbers Birthday")
    private LocalDate birthday;

    @Schema(description = "Any additional information about the clubber")
    private String additionalInfo;

    @Schema(description = "Clubbers parent/guardian")
    private Parent parent;

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

    public ChurchGroup getGroup() {
        return group;
    }

    public void setGroup(ChurchGroup group) {
        this.group = group;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }
}
