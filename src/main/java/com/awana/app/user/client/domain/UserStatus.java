/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.app.user.client.domain;

import com.awana.common.enums.AccountStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Class to create a user status object
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
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

    public UserStatus() {}

    public UserStatus(int userId, AccountStatus accountStatus, Boolean appAccess) {
        this.userId = userId;
        this.accountStatus = accountStatus;
        this.appAccess = appAccess;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Boolean isAppAccess() {
        return appAccess;
    }

    public void setAppAccess(Boolean appAccess) {
        this.appAccess = appAccess;
    }

    public Integer getUpdatedUserId() {
        return updatedUserId;
    }

    public void setUpdatedUserId(Integer updatedUserId) {
        this.updatedUserId = updatedUserId;
    }
}
