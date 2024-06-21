-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.2.0.2__Add_VBS_Points_Config_Table.sql
-- Author: Sam Butler
-- Date: June 19, 2024
-- Version: V1.2.0.2
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- START
-- ---------------------------------------------------------------------------------

CREATE TABLE vbs_theme_groups (
  vbs_theme_id  INT         UNSIGNED NOT NULL,
  church_group  VARCHAR(45)          NOT NULL,
  name          VARCHAR(50),
  PRIMARY KEY (vbs_theme_id,church_group)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX IX_vbs_theme_groups__church_group ON vbs_theme_groups(church_group);

ALTER TABLE vbs_theme_groups ADD CONSTRAINT FK1_vbs_theme_groups__vbs_themes
  FOREIGN KEY (vbs_theme_id) REFERENCES vbs_themes (id) 
    ON DELETE CASCADE 
    ON UPDATE CASCADE;

-- ---------------------------------------------------------------------------------
-- END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION