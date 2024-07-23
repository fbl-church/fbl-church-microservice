-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.2.0.1__Add_VBS_Points_Config_Table.sql
-- Author: Sam Butler
-- Date: June 19, 2024
-- Version: V1.2.0.1
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- START
-- ---------------------------------------------------------------------------------

CREATE TABLE vbs_points_config (
  id                INT          UNSIGNED NOT NULL AUTO_INCREMENT,
  type              VARCHAR(128)          NOT NULL,
  display_name      VARCHAR(128)          NOT NULL,
  points            INT          UNSIGNED NOT NULL,
  registration_only BIT                   NOT NULL DEFAULT 0,
  check_in_apply    BIT                   NOT NULL DEFAULT 0,
  `enabled`         BIT                   NOT NULL DEFAULT 1,
  vbs_theme_id      INT          UNSIGNED NOT NULL,
  updated_user_id   INT          UNSIGNED          DEFAULT NULL,
  updated_date      DATETIME              NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  insert_user_id    INT          UNSIGNED          DEFAULT NULL,
  insert_date       DATETIME              NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE UNIQUE INDEX UX_vbs_points_config__type_vbs_theme_id ON vbs_points_config(type,vbs_theme_id);
CREATE INDEX IX_vbs_points_config__vbs_theme_id ON vbs_points_config(vbs_theme_id);
CREATE INDEX IX_vbs_points_config__updated_user_id ON vbs_points_config(updated_user_id);
CREATE INDEX IX_vbs_points_config__insert_user_id ON vbs_points_config(insert_user_id);

ALTER TABLE vbs_points_config ADD CONSTRAINT FK3_vbs_points_config__vbs_themes
  FOREIGN KEY (vbs_theme_id) REFERENCES vbs_themes (id) 
    ON DELETE CASCADE
    ON UPDATE CASCADE;
   
ALTER TABLE vbs_points_config ADD CONSTRAINT FK1_vbs_points_config__users
  FOREIGN KEY (updated_user_id) REFERENCES users (id) 
    ON DELETE SET NULL
    ON UPDATE CASCADE;

ALTER TABLE vbs_points_config ADD CONSTRAINT FK2_vbs_points_config__users
  FOREIGN KEY (insert_user_id) REFERENCES users (id) 
    ON DELETE SET NULL
    ON UPDATE CASCADE;

-- ---------------------------------------------------------------------------------
-- END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION