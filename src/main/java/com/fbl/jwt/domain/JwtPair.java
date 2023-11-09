/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.jwt.domain;

import java.security.Key;
import java.time.LocalDateTime;

import com.fbl.common.date.LocalDateFormatter;

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

    public JwtPair(String token, Key signingKey) {
        this.token = token;
        this.claimSet = Jwts.parserBuilder().setSigningKey(signingKey).build()
                .parseClaimsJws(token).getBody();
    }

    public LocalDateTime getIssuedAt() {
        return LocalDateFormatter.toLocalDateTime(this.claimSet.getIssuedAt());
    }

    public LocalDateTime getExpiration() {
        return LocalDateFormatter.toLocalDateTime(this.claimSet.getExpiration());
    }
}
