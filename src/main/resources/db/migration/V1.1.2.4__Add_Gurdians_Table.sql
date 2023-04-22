-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.1.2.4__Add_Clubber_Parents_Table.sql
-- Author: Sam Butler
-- Date: April 24, 2022
-- Issue: AWANA-2: Add Parents Table 
-- Version: v1.1.2
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- AWANA-2: START
-- ---------------------------------------------------------------------------------

CREATE TABLE gurdians (
  id           INT          unsigned NOT NULL AUTO_INCREMENT,
  first_name   VARCHAR(128)          NOT NULL,
  last_name    VARCHAR(128)          NOT NULL,
  relationship VARCHAR(128)          NOT NULL  DEFAULT 'OTHER'
  email        VARCHAR(128)                    DEFAULT NULL,
  phone        VARCHAR(128)          NOT NULL,
  address      VARCHAR(128)                    DEFAULT NULL,
  insert_date  DATETIME              NOT NULL  DEFAULT CURRENT_TIMESTAMP(),
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ---------------------------------------------------------------------------------
-- AWANA-2: END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION


