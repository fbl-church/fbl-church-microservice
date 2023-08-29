/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.jwt.domain;

import com.fbl.environment.EnvironmentService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Getter;

/**
 * Jwt Pair class for storing the token and claims.
 * 
 * @author Sam Butler
 * @since August 22, 2022
 */
@Getter
public final class JwtPair {
    private final String token;
    private final Claims claimSet;

    public JwtPair(String token, EnvironmentService environmentService) {
        this.token = token;
        this.claimSet = (Claims) Jwts.parser().setSigningKey(environmentService.getSigningKey()).parse(token)
                .getBody();
    }
}
