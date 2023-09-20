-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.1.3.2__Add_Feature_Table.sql
-- Author: Sam Butler
-- Date: April 24, 2022
-- Version: V1.1.3.1
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- START
-- ---------------------------------------------------------------------------------

CREATE TABLE features (
  id            INT          UNSIGNED NOT NULL AUTO_INCREMENT,
  app_id        INT          UNSIGNED NOT NULL,
  `key`         VARCHAR(128)          NOT NULL,
  enabled       BIT                   NOT NULL DEFAULT 0,
  insert_date   DATETIME              NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE UNIQUE INDEX UX_features__app_id_key ON features(app_id,`key`);

ALTER TABLE features ADD CONSTRAINT FK1_features__applications
  FOREIGN KEY(app_id) REFERENCES applications (id)
    ON DELETE CASCADE 
    ON UPDATE CASCADE;

-- ---------------------------------------------------------------------------------
-- END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION