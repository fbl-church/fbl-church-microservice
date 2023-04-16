/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.app.user.client.domain;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Class to create a clubber object
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Schema(description = "Clubber object for holding user details.")
public class Clubber extends User {

    @Schema(description = "Clubbers Allergies, can be null")
    private String allergies;

    @Schema(description = "Clubbers Birthday")
    private String birthday;

    @Schema(description = "Clubbers parents name")
    private String parentName;

    @Schema(description = "Any additional information about the clubber")
    private String additionalInfo;

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
