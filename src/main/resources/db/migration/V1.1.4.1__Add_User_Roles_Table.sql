-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.1.4.1__Add_User_Roles_Table.sql
-- Author: Sam Butler
-- Date: April 24, 2022
-- Version: V1.1.4.1
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- START
-- ---------------------------------------------------------------------------------

CREATE TABLE user_roles (
  user_id   INT         unsigned NOT NULL,
  web_role  VARCHAR(45)          NOT NULL,
  PRIMARY KEY (user_id,web_role)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX IX_user_roles__web_role ON user_roles(web_role);

ALTER TABLE user_roles ADD CONSTRAINT FK1_user_roles__users
  FOREIGN KEY(user_id) REFERENCES users(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE;

-- ---------------------------------------------------------------------------------
-- END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION