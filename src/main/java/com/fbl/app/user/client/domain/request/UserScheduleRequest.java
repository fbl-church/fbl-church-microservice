/*
 * Copyright of FBL Church App. All rights reserved.
*/
package com.fbl.app.user.client.domain.request;

import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Description
 *
 * @author Sam Butler
 * @since Feb 14, 2024 02
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User get request object for filtering users schedules.")
public class UserScheduleRequest {
    @Schema(description = "List of user ids.")
    private Set<Integer> userId;

    @Schema(description = "List of months to filter by")
    private Set<Integer> months;
}
