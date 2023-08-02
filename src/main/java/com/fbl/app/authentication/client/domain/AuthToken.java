/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.authentication.client.domain;

import java.time.LocalDateTime;

import com.fbl.app.user.client.domain.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Authentication token to be used within the app.
 *
 * @author Sam Butler
 * @since July 31, 2021
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User Auth Token")
public class AuthToken {

    @Schema(description = "JWT Token for the user.")
    private String token;

    @Schema(description = "When the token was created.")
    private LocalDateTime createDate;

    @Schema(description = "When the token expires.")
    private LocalDateTime expireDate;

    @Schema(description = "Data to be attached to the auth token.")
    private User user;
}
