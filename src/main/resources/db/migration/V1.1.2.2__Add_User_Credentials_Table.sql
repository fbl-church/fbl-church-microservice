-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.1.2.2__Add_User_Credentials_Table.sql
-- Author: Sam Butler
-- Date: April 24, 2022
-- Version: v1.1.2
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- START
-- ---------------------------------------------------------------------------------
CREATE TABLE user_credentials (
  user_id   INT        UNSIGNED NOT NULL,
  password  BINARY(60)          NOT NULL,
  PRIMARY KEY (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE user_credentials ADD CONSTRAINT users__user_credentials__FK1 
  FOREIGN KEY (user_id) REFERENCES users (id) 
    ON DELETE CASCADE 
    ON UPDATE CASCADE;

-- ---------------------------------------------------------------------------------
-- END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION