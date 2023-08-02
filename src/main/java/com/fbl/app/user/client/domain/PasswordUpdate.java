/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.user.client.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Object used to update a users password. This will hold a current password and
 * new password fields.
 * 
 * @author Sam Butler
 * @since October 29, 2021
 */
@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Password update object for when a user wants to change their password.")
public class PasswordUpdate {

    @Schema(description = "The users current password.")
    private String currentPassword;

    @Schema(description = "The users new password")
    private String newPassword;
}
