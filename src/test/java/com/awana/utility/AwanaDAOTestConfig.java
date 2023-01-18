package com.awana.utility;

import org.springframework.context.annotation.ComponentScan;

/**
 * Defines the the base component scan packages for the DAO.
 * 
 * @author Sam Butler
 * @since April 25, 2022
 */
@ComponentScan(basePackages = {"com.awana.app.user.dao", "com.awana.app.authentication.dao"})
public class AwanaDAOTestConfig {}