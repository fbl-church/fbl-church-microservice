package com.fbl.app.email.client.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Description
 * 
 * @author Sam Butler
 * @since Mar 02, 2024
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Contact Admin Email Data for populating the template")
public class ContactAdminEmailData {
    private String username;
    private String emailMessage;
}
