package com.fbl.app.user.client.domain.jpa;

import java.time.LocalDateTime;
import java.util.List;

import com.fbl.common.enums.AccountStatus;
import com.fbl.common.enums.ThemeType;
import com.fbl.common.enums.WebRole;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Description
 * 
 * @author Sam Butler
 * @since Jul 29, 2024
 */
@Getter
@Setter
public class UserContract {
    private Integer id;

    @NotBlank(message = "Invalid firstName: Can not be empty or null")
    private String firstName;

    @NotBlank(message = "Invalid lastName: Can not be empty or null")
    private String lastName;

    private String email;

    private List<WebRole> webRole;

    private ThemeType theme;

    private Boolean appAccess;

    private AccountStatus accountStatus;

    private LocalDateTime lastLoginDate;

    private LocalDateTime insertDate;
}
