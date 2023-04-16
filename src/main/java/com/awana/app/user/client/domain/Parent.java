package com.awana.app.user.client.domain;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Class to create a parent object
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Schema(description = "Parent object for holding parent details")
public class Parent {

    @Schema(description = "Parents name")
    private String name;

    @Schema(description = "Parents email")
    private String email;

    @Schema(description = "Parents phone number")
    private String phone;

    @Schema(description = "Parents address")
    private String address;
}
