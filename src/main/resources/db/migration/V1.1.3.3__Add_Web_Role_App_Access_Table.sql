-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.1.3.3__Add_Web_Role_App_Access_Table.sql
-- Author: Sam Butler
-- Date: April 24, 2022
-- Issue: FBL-2: Create Web Role App Access Table 
-- Version: V1.1.3.3
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- FBL-2: START
-- ---------------------------------------------------------------------------------

CREATE TABLE web_role_app_access (
  web_role VARCHAR(45)          NOT NULL,
  app_id   INT         unsigned NOT NULL,
  access   TINYINT(4)  unsigned NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX web_role_app_access_IDX1 ON web_role_app_access(web_role);
CREATE INDEX web_role_app_access_IDX2 ON web_role_app_access(app_id);
CREATE UNIQUE INDEX web_role_app_access_AK1 ON web_role_app_access(web_role,app_id);

ALTER TABLE web_role_app_access 
  ADD CONSTRAINT user_profile__web_role_app_access__FK1 FOREIGN KEY(app_id) REFERENCES application(id)
    ON DELETE CASCADE ON UPDATE CASCADE;
-- ---------------------------------------------------------------------------------
-- FBL-2: END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION