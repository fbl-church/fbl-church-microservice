/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.app.user.client.domain;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.awana.common.enums.AccountStatus;
import com.awana.common.enums.WebRole;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Class to create a user object
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Schema(description = "User object for holding user details.")
public class User {

	@Schema(description = "User identifier")
	private int id;

	@Schema(description = "First name of the user.")
	@NotBlank(message = "Invalid firstName: Can not be empty or null")
	private String firstName;

	@Schema(description = "Last name of the user.")
	@NotBlank(message = "Invalid lastName: Can not be empty or null")
	private String lastName;

	@Schema(description = "The users email")
	@NotBlank(message = "Invalid Email: Can not be empty or null")
	private String email;

	@Schema(description = "The user web role")
	@NotNull(message = "Invalid webRole: Can not be null")
	private WebRole webRole;

	@Schema(description = "The access of the user to the website.")
	private Boolean appAccess;

	@Schema(description = "The user account status.")
	private AccountStatus accountStatus;

	@Schema(description = "The users password (hashed).")
	@NotBlank(message = "Invalid password: Can not be empty or null")
	private String password;

	@Schema(description = "The date the user has last authenticated.")
	private LocalDateTime lastLoginDate;

	@Schema(description = "When the user was created.")
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

	public void setLastName(String lastname) {
		this.lastName = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public WebRole getWebRole() {
		return webRole;
	}

	public void setWebRole(WebRole webRole) {
		this.webRole = webRole;
	}

	public Boolean getAppAccess() {
		return appAccess;
	}

	public void setAppAccess(Boolean appAccess) {
		this.appAccess = appAccess;
	}

	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public LocalDateTime getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(LocalDateTime lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public LocalDateTime getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(LocalDateTime insertDate) {
		this.insertDate = insertDate;
	}
}
