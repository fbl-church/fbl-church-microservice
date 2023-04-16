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

CREATE TABLE parents (
  clubber_id INT unsigned NOT NULL,
  name       VARCHAR(128) NOT NULL,
  email      VARCHAR(128)           DEFAULT NULL,
  phone      VARCHAR(128) NOT NULL,
  address    VARCHAR(128)           DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE parents ADD CONSTRAINT clubbers__parents__FK1 
  FOREIGN KEY (clubber_id) REFERENCES clubbers (id) 
    ON DELETE CASCADE
    ON UPDATE CASCADE;

-- ---------------------------------------------------------------------------------
-- AWANA-2: END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION


