package com.fbl.app.user.client.domain;

import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A Users Feature Access
 * 
 * @author Sam Butler
 * @since May 20, 2021
 */
@Data
@AllArgsConstructor
public class UserAccess {
    @Schema(description = "The user information")
    private User user;

    @Schema(description = "The user features they have access too")
    private Map<String, List<Map<String, String>>> features;

    @Schema(description = "The user applications they have access too")
    private List<String> applications;
}
