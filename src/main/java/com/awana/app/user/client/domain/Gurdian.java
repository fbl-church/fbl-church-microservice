package com.awana.app.user.client.domain;

import com.awana.common.enums.GurdianType;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Gurdian Object class
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Schema(description = "Gurdian object for holding gurdian details for a clubber")
public class Gurdian {

    @Schema(description = "Parents name")
    private String name;

    @Schema(description = "Parents email")
    private String email;

    @Schema(description = "Parents phone number")
    private String phone;

    @Schema(description = "Parents address")
    private String address;

    @Schema(description = "Gurdian relation to the clubber")
    private GurdianType relation;
}
