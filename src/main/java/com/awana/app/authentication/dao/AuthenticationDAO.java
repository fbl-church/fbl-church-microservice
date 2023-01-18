package com.awana.app.authentication.dao;

import javax.sql.DataSource;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import com.awana.common.exception.NotFoundException;
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
     * @throws Exception If there is not user for the given email.
     */
    public String getUserAuthPassword(String email) throws Exception {
        try {
            return get(getSql("getUserHashedPassword"), parameterSource(EMAIL, email), String.class);
        }
        catch(Exception e) {
            throw new NotFoundException("User Email", email);
        }
    }
}
