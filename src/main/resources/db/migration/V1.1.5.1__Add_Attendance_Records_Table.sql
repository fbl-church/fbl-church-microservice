-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.1.5.1__Add_Attendance_Records_Table.sql
-- Author: Sam Butler
-- Date: April 24, 2022
-- Version: V1.1.5.1
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- START
-- ---------------------------------------------------------------------------------

CREATE TABLE attendance_records (
  id            INT          UNSIGNED NOT NULL AUTO_INCREMENT,
  name          VARCHAR(128)          NOT NULL,
  status        VARCHAR(32)           NOT NULL DEFAULT 'PENDING',
  type          VARCHAR(32)           NOT NULL,
  active_date   DATE                  NOT NULL DEFAULT (CURDATE()),
  closed_date   DATE                           DEFAULT NULL,
  insert_date   DATETIME              NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE UNIQUE INDEX UX_attendance_records__type_active_date ON attendance_records(type,active_date);

-- ---------------------------------------------------------------------------------
-- END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION