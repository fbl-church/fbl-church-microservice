-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.1.4.1__Add_User_Roles_Table.sql
-- Author: Sam Butler
-- Date: April 24, 2022
-- Issue: FBL-2: Create User Roles Table
-- Version: V1.1.4.1
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- FBL-2: START
-- ---------------------------------------------------------------------------------

CREATE TABLE user_roles (
  user_id   INT         unsigned NOT NULL,
  web_role  VARCHAR(45)          NOT NULL,
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX user_roles_IDX1 ON user_roles(user_id);
CREATE INDEX user_roles_IDX2 ON user_roles(web_role);
CREATE UNIQUE INDEX user_roles_AK1 ON user_roles(user_id,web_role);

ALTER TABLE user_roles 
  ADD CONSTRAINT users__user_roles__FK1 FOREIGN KEY(user_id) REFERENCES users(id)
    ON DELETE CASCADE ON UPDATE CASCADE;
-- ---------------------------------------------------------------------------------
-- FBL-2: END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION