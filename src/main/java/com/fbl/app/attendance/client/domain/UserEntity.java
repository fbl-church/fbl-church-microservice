package com.fbl.app.attendance.client.domain;

import java.time.LocalDateTime;
import java.util.List;

import com.fbl.common.enums.ThemeType;
import com.fbl.common.enums.WebRole;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Description
 * 
 * @author Sam Butler
 * @since Jun 28, 2024
 */
@Entity
@Table(name = "users")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private String email;

    @ElementCollection(targetClass = WebRole.class)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "web_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<WebRole> webRole;

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private ThemeType theme;

    // @Column(name = "app_access")
    // private Boolean appAccess;

    // @Column(name = "account_status")
    // @NotNull
    // @Enumerated(EnumType.STRING)
    // private AccountStatus accountStatus;

    @Column(name = "last_login_date")
    private LocalDateTime lastLoginDate;

    @Column(name = "insert_date")
    private LocalDateTime insertDate;
}
