-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.1.2.6__Add_Clubber_Gurdians_Table.sql
-- Author: Sam Butler
-- Date: April 24, 2022
-- Issue: AWANA-2: Add Clubber Gurdians Table 
-- Version: v1.1.2
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- AWANA-2: START
-- ---------------------------------------------------------------------------------

CREATE TABLE clubber_gurdians (
  clubber_id INT UNSIGNED NOT NULL,
  gurdian_id INT UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE UNIQUE INDEX clubber_gurdians_AK1 ON clubber_gurdians(clubber_id, gurdian_id);

ALTER TABLE clubber_gurdians ADD CONSTRAINT clubbers__clubber_gurdians__FK1 
  FOREIGN KEY (clubber_id) REFERENCES clubbers (id) 
    ON DELETE CASCADE
    ON UPDATE CASCADE;

ALTER TABLE clubber_gurdians ADD CONSTRAINT gurdians__clubber_gurdians__FK2
  FOREIGN KEY (gurdian_id) REFERENCES gurdians (id) 
    ON DELETE CASCADE
    ON UPDATE CASCADE;

-- ---------------------------------------------------------------------------------
-- AWANA-2: END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION


