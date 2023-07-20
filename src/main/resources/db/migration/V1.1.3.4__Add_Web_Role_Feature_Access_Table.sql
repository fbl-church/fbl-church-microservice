-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.1.3.4__Add_Web_Role_Feature_Access_Table.sql
-- Author: Sam Butler
-- Date: April 24, 2022
-- Version: V1.1.3.4
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- MARCS-2: START
-- ---------------------------------------------------------------------------------

CREATE TABLE web_role_feature_access (
  feature_id INT         unsigned NOT NULL,
  web_role   VARCHAR(45)          NOT NULL,
  `create`   TINYINT(4)  unsigned NOT NULL DEFAULT 0,
  `read`     TINYINT(4)  unsigned NOT NULL DEFAULT 0,
  `update`   TINYINT(4)  unsigned NOT NULL DEFAULT 0,
  `delete`   TINYINT(4)  unsigned NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE UNIQUE INDEX web_role_feature_access_AK1 ON web_role_feature_access(web_role,feature_id);
CREATE INDEX web_role_feature_access_IDX2 ON web_role_feature_access(feature_id);

ALTER TABLE web_role_feature_access 
  ADD CONSTRAINT users__web_role_feature_access__FK1 FOREIGN KEY(feature_id) REFERENCES feature_access(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE;

-- ---------------------------------------------------------------------------------
-- END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION