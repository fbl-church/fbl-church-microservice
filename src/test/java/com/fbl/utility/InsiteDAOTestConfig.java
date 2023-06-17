/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.utility;

import org.springframework.context.annotation.ComponentScan;

/**
 * Defines the the base component scan packages for the DAO.
 * 
 * @author Sam Butler
 * @since April 25, 2022
 */
@ComponentScan(basePackages = {"com.fbl.app.user.dao", "com.fbl.app.authentication.dao"})
public class InsiteDAOTestConfig {}