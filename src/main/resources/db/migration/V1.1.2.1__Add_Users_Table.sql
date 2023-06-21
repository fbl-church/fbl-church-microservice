-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.1.2.1__Add_Users_Table.sql
-- Author: Sam Butler
-- Date: April 24, 2022
-- Version: v1.1.1
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- START
-- ---------------------------------------------------------------------------------

CREATE TABLE users (
  id                       INT          UNSIGNED NOT NULL AUTO_INCREMENT,
  first_name               VARCHAR(128)          NOT NULL,
  last_name                VARCHAR(128)          NOT NULL DEFAULT '',
  email                    VARCHAR(128)                   DEFAULT NULL,
  web_role                 VARCHAR(45)           NOT NULL DEFAULT 'USER',
  last_login_date          DATETIME              NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  insert_date              DATETIME              NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE UNIQUE INDEX users_AK1 ON users(email);

-- ---------------------------------------------------------------------------------
-- END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION