-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.2.0.0__Add_VBS_Themes_Table.sql
-- Author: Sam Butler
-- Date: May 06, 2024
-- Version: V1.2.0.0
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- START
-- ---------------------------------------------------------------------------------

CREATE TABLE vbs_themes (
  id                     INT          UNSIGNED NOT NULL AUTO_INCREMENT,
  name                   VARCHAR(128)          NOT NULL,
  year                   INT          UNSIGNED NOT NULL,
  status                 VARCHAR(32)           NOT NULL DEFAULT 'PENDING',
  money                  FLOAT(8,2)            NOT NULL DEFAULT 0.00,
  children_attended      INT          UNSIGNED NOT NULL DEFAULT 0,
  donation               VARCHAR(256)          NOT NULL,
  insert_date            DATETIME              NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE UNIQUE INDEX UX_vbs_themes__name_year ON vbs_themes(name,year);

-- ---------------------------------------------------------------------------------
-- END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION