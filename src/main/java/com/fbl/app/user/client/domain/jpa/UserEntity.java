package com.fbl.app.user.client.domain.jpa;

import java.time.LocalDateTime;
import java.util.List;

import com.fbl.common.enums.AccountStatus;
import com.fbl.common.enums.ThemeType;
import com.fbl.common.enums.WebRole;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "web_role", nullable = false)
    @ElementCollection(targetClass = WebRole.class)
    @Enumerated(EnumType.STRING)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    private List<WebRole> webRole;

    @Column(name = "theme")
    @Enumerated(EnumType.STRING)
    private ThemeType theme;

    @Column(name = "app_access")
    private Boolean appAccess;

    @Column(name = "account_status")
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @Column(name = "last_login_date")
    private LocalDateTime lastLoginDate;

    @Column(name = "insert_date")
    private LocalDateTime insertDate;
}
