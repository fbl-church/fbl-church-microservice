-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.1.3.4__Add_Web_Role_Feature_Access_Table.sql
-- Author: Sam Butler
-- Date: April 24, 2022
-- Version: V1.1.3.4
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- START
-- ---------------------------------------------------------------------------------

CREATE TABLE web_role_feature_access (
  feature_id INT         UNSIGNED NOT NULL,
  web_role   VARCHAR(45)          NOT NULL,
  `create`   BIT                  NOT NULL DEFAULT 0,
  `read`     BIT                  NOT NULL DEFAULT 0,
  `update`   BIT                  NOT NULL DEFAULT 0,
  `delete`   BIT                  NOT NULL DEFAULT 0,
  PRIMARY KEY (feature_id,web_role)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX IX_web_role_feature_access__web_role ON web_role_feature_access(web_role);

ALTER TABLE web_role_feature_access ADD CONSTRAINT FK1_web_role_feature_access__features
FOREIGN KEY(feature_id) REFERENCES features (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE;

-- ---------------------------------------------------------------------------------
-- END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION