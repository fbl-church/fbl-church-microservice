/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.children.client.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fbl.app.gurdian.client.domain.Gurdian;
import com.fbl.common.enums.ChurchGroup;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Class to create a child object
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Schema(description = "Child object for holding child details")
public class Child {

    @Schema(description = "Child identifier")
    private int id;

    @Schema(description = "First name of the child")
    @NotBlank(message = "Invalid firstName: Can not be empty or null")
    private String firstName;

    @Schema(description = "Last name of the child")
    @NotBlank(message = "Invalid lastName: Can not be empty or null")
    private String lastName;

    @Schema(description = "The user church group")
    @NotNull(message = "Invalid churchGroup: Can not be null")
    private ChurchGroup churchGroup;

    @Schema(description = "Childs Allergies, can be null")
    private String allergies;

    @Schema(description = "Childs Birthday")
    private LocalDate birthday;

    @Schema(description = "Any additional information about the child")
    private String additionalInfo;

    @Schema(description = "Childs guardians")
    @NotEmpty
    private List<Gurdian> gurdians;

    @Schema(description = "When the child was created.")
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

    public ChurchGroup getChurchGroup() {
        return churchGroup;
    }

    public void setChurchGroup(ChurchGroup churchGroup) {
        this.churchGroup = churchGroup;
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

    public List<Gurdian> getGurdians() {
        return gurdians;
    }

    public void setGurdians(List<Gurdian> gurdians) {
        this.gurdians = gurdians;
    }

    public LocalDateTime getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(LocalDateTime insertDate) {
        this.insertDate = insertDate;
    }
}
