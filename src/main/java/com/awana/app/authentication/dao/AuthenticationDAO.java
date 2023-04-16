/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.app.authentication.dao;

import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import com.awana.sql.abstracts.BaseDao;

/**
 * Class that handles all the dao calls to the database for users
 * 
 * @author Sam Butler
 * @since June 25, 2021
 */
@Repository
public class AuthenticationDAO extends BaseDao {

    public AuthenticationDAO(DataSource source) {
        super(source);
    }

    /**
     * Get the {@link BCrypt} hashed password for the given email.
     * 
     * @param email The email assocaited with the user.
     * @return {@link String} of the hashed password.
     */
    public Optional<String> getUserAuthPassword(String email) {
        return getOptional("getUserHashedPassword", parameterSource(EMAIL, email), String.class);
    }
}
