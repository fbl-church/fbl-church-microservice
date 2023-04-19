-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.1.2.3__Add_Clubbers_Table.sql
-- Author: Sam Butler
-- Date: April 24, 2022
-- Issue: AWANA-2: Clubbers Table 
-- Version: v1.1.2
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- AWANA-2: START
-- ---------------------------------------------------------------------------------

CREATE TABLE clubbers (
  id              INT unsigned NOT NULL AUTO_INCREMENT,
  first_name      VARCHAR(128) NOT NULL,
  last_name       VARCHAR(128) NOT NULL,
  church_group    VARCHAR(45)            DEFAULT NULL,
  birthday        VARCHAR(45)            DEFAULT NULL,
  allergies       LONGTEXT               DEFAULT NULL,
  additional_info LONGTEXT               DEFAULT NULL,
  insert_date     DATETIME    NOT NULL   DEFAULT CURRENT_TIMESTAMP(),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- ---------------------------------------------------------------------------------
-- AWANA-2: END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION


