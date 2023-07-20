-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.1.2.3__Add_Children_Table.sql
-- Author: Sam Butler
-- Date: April 24, 2022
-- Version: v1.1.2
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- START
-- ---------------------------------------------------------------------------------

CREATE TABLE children (
  user_id         INT         unsigned NOT NULL,
  birthday        VARCHAR(45)          DEFAULT NULL,
  allergies       LONGTEXT             DEFAULT NULL,
  additional_info LONGTEXT             DEFAULT NULL,
  PRIMARY KEY (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE children ADD CONSTRAINT users__children__FK1 
  FOREIGN KEY (user_id) REFERENCES users (id) 
    ON DELETE CASCADE 
    ON UPDATE CASCADE;

-- ---------------------------------------------------------------------------------
-- END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION


