/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.user.client.domain;

import java.time.LocalDateTime;
import java.util.List;

import com.fbl.common.enums.AccountStatus;
import com.fbl.common.enums.WebRole;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Class to create a user object
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Getter
@Setter
@Schema(description = "User object for holding user details.")
public class User {

	@Schema(description = "User identifier")
	private Integer id;

	@Schema(description = "First name of the user.")
	@NotBlank(message = "Invalid firstName: Can not be empty or null")
	private String firstName;

	@Schema(description = "Last name of the user.")
	@NotBlank(message = "Invalid lastName: Can not be empty or null")
	private String lastName;

	@Schema(description = "The users email")
	private String email;

	@Schema(description = "The user web role")
	private List<WebRole> webRole;

	@Schema(description = "The access of the user to the website.")
	private Boolean appAccess;

	@Schema(description = "The user account status.")
	private AccountStatus accountStatus;

	@Schema(description = "The users password")
	private String password;

	@Schema(description = "The date the user has last authenticated.")
	private LocalDateTime lastLoginDate;

	@Schema(description = "When the user was created.")
	private LocalDateTime insertDate;
}
