package com.awana.app.gurdian.client.domain;

import com.awana.common.enums.GurdianType;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Gurdian Object class
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Schema(description = "Gurdian object for holding gurdian details for a gurdian")
public class Gurdian {

    @Schema(description = "Gurdian id the gurdian belongs too")
    private int gurdianId;

    @Schema(description = "Gurdians name")
    private String name;

    @Schema(description = "Gurdians email")
    private String email;

    @Schema(description = "Gurdian relation to the gurdian")
    private GurdianType relation;

    @Schema(description = "Gurdians phone number")
    private String phone;

    @Schema(description = "Gurdians address")
    private String address;

    public int getGurdianId() {
        return gurdianId;
    }

    public void setGurdianId(int gurdianId) {
        this.gurdianId = gurdianId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public GurdianType getRelation() {
        return relation;
    }

    public void setRelation(GurdianType relation) {
        this.relation = relation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
