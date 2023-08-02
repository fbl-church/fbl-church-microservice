/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.authentication.client.domain.request;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * AuthenticationRequest for authenticating and updating user credentials.
 *
 * @author Sam Butler
 * @since August 1, 2020
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Authentication Request")
public class AuthenticationRequest implements Serializable {

    @Schema(description = "The email to authenticate with.")
    private String email;

    @Schema(description = "The password associated with the email.")
    private String password;
}
