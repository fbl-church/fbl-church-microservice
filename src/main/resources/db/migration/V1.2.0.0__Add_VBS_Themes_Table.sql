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
  id                  INT          UNSIGNED NOT NULL AUTO_INCREMENT,
  name                VARCHAR(128)          NOT NULL,
  start_date          DATE                  NOT NULL DEFAULT (CURDATE()),
  end_date            DATE                  NOT NULL DEFAULT (CURDATE()),
  status              VARCHAR(32)           NOT NULL DEFAULT 'PENDING',
  money               FLOAT(8,2)            NOT NULL DEFAULT 0.00,
  children_registered INT          UNSIGNED NOT NULL DEFAULT 0,
  donation            VARCHAR(256)          NOT NULL,
  closed_by_user_id   INT          UNSIGNED          DEFAULT NULL,
  insert_date         DATETIME              NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE UNIQUE INDEX UX_vbs_themes__name_start_date ON vbs_themes(name,start_date);
CREATE INDEX IX_vbs_themes__closed_by_user_id ON attendance_records(closed_by_user_id);

ALTER TABLE vbs_themes ADD CONSTRAINT FK1_vbs_themes__users
  FOREIGN KEY (closed_by_user_id) REFERENCES users (id) 
    ON DELETE SET NULL 
    ON UPDATE CASCADE;

-- ---------------------------------------------------------------------------------
-- END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION