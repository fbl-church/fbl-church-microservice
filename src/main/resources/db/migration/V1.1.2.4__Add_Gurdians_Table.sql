-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.1.2.4__Add_Gurdians_Table.sql
-- Author: Sam Butler
-- Date: April 24, 2022
-- Version: v1.1.2
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- START
-- ---------------------------------------------------------------------------------

CREATE TABLE gurdians (
  id           INT unsigned NOT NULL AUTO_INCREMENT,
  first_name   VARCHAR(128) NOT NULL,
  last_name    VARCHAR(128) NOT NULL,
  email        VARCHAR(128)          DEFAULT NULL,
  phone        VARCHAR(128) NOT NULL,
  address      VARCHAR(128)          DEFAULT NULL,
  insert_date  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  PRIMARY KEY (id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

CREATE UNIQUE INDEX gurdians_AK1 ON gurdians(email);
-- ---------------------------------------------------------------------------------
-- END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION


