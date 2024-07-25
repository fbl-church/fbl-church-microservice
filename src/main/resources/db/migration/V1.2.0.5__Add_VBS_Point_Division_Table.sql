-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.2.0.5__Add_VBS_Point_Division_Table.sql
-- Author: Sam Butler
-- Date: June 27, 2024
-- Version: V1.2.0.5
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- START
-- ---------------------------------------------------------------------------------

CREATE TABLE vbs_point_division (
  id                INT          UNSIGNED NOT NULL AUTO_INCREMENT,
  min               INT          UNSIGNED NOT NULL,
  max               INT          UNSIGNED NOT NULL,
  color             VARCHAR(50)           NOT NULL,
  vbs_theme_id      INT          UNSIGNED NOT NULL,
  updated_user_id   INT          UNSIGNED          DEFAULT NULL,
  updated_date      DATETIME              NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  insert_user_id    INT          UNSIGNED          DEFAULT NULL,
  insert_date       DATETIME              NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX IX_vbs_point_division__vbs_theme_id ON vbs_point_division(vbs_theme_id);
CREATE INDEX IX_vbs_point_division__updated_user_id ON vbs_point_division(updated_user_id);
CREATE INDEX IX_vbs_point_division__insert_user_id ON vbs_point_division(insert_user_id);

ALTER TABLE vbs_point_division ADD CONSTRAINT FK1_vbs_point_division__users
  FOREIGN KEY (updated_user_id) REFERENCES users (id) 
    ON DELETE SET NULL
    ON UPDATE CASCADE;

ALTER TABLE vbs_point_division ADD CONSTRAINT FK2_vbs_point_division__users
  FOREIGN KEY (insert_user_id) REFERENCES users (id) 
    ON DELETE SET NULL
    ON UPDATE CASCADE;
    
ALTER TABLE vbs_point_division ADD CONSTRAINT FK3_vbs_point_division__vbs_themes
  FOREIGN KEY (vbs_theme_id) REFERENCES vbs_themes (id) 
    ON DELETE CASCADE
    ON UPDATE CASCADE;
   

-- ---------------------------------------------------------------------------------
-- END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION