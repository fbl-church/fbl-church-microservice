/*
 * Copyright of FBL Church App. All rights reserved.
*/
package com.fbl.app.user.client.domain;

import java.time.LocalDate;

import com.fbl.common.enums.AttendanceStatus;
import com.fbl.common.enums.ChurchGroup;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * User Schedule Object
 *
 * @author Sam Butler
 * @since Feb 14, 2024 02
 */
@Getter
@Setter
@Schema(description = "User object for holding user schedule.")
public class UserSchedule {
    private int recordId;
    private int userId;
    private String recordName;
    private AttendanceStatus status;
    private ChurchGroup type;
    private LocalDate activeDate;
}
