/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.children.client.domain;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.fbl.app.gurdian.client.domain.Gurdian;
import com.fbl.app.user.client.domain.User;
import com.fbl.common.enums.ChurchGroup;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Class to create a child object
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Schema(description = "Child object for holding child details")
public class Child extends User {

    @Schema(description = "The user church group")
    private List<ChurchGroup> churchGroup;

    @Schema(description = "Childs Allergies, can be null")
    private String allergies;

    @Schema(description = "Childs Birthday")
    private LocalDate birthday;

    @Schema(description = "Any additional information about the child")
    private String additionalInfo;

    @Schema(description = "Childs guardians")
    @NotEmpty
    private List<Gurdian> gurdians;

    public List<ChurchGroup> getChurchGroup() {
        return churchGroup;
    }

    public void setChurchGroup(List<ChurchGroup> churchGroup) {
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
}
