-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.1.3.3__Add_Web_Role_App_Access_Table.sql
-- Author: Sam Butler
-- Date: April 24, 2022
-- Version: V1.1.3.3
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- START
-- ---------------------------------------------------------------------------------

CREATE TABLE web_role_app_access (
  web_role VARCHAR(45)          NOT NULL,
  app_id   INT         UNSIGNED NOT NULL,
  access   BIT                  NOT NULL DEFAULT 0,
  PRIMARY KEY (web_role,app_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX IX_web_role_app_access__app_id ON web_role_app_access(app_id);

ALTER TABLE web_role_app_access ADD CONSTRAINT FK1_web_role_app_access__applications
  FOREIGN KEY(app_id) REFERENCES applications (id)
    ON DELETE CASCADE 
    ON UPDATE CASCADE;

-- ---------------------------------------------------------------------------------
-- END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION