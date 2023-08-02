/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.user.client.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fbl.common.enums.AccountStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class to create a user status object
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "The User status request object.")
public class UserStatus {

    @Schema(description = "The user id of the user status.")
    private int userId;

    @Schema(description = "The account status of the user.")
    private AccountStatus accountStatus;

    @Schema(description = "The app access of the user.")
    private Boolean appAccess;

    @JsonInclude(Include.NON_DEFAULT)
    @Schema(description = "The last update user id of the user.")
    private Integer updatedUserId;
}
