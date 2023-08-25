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
  attendance_id INT          UNSIGNED NOT NULL AUTO_INCREMENT,
  name          VARCHAR(128)          NOT NULL,
  status        VARCHAR(32)           NOT NULL,
  type          VARCHAR(32)           NOT NULL,
  active_date   DATE                  NOT NULL DEFAULT (CURDATE()),
  closed_date   DATE                           DEFAULT NULL,
  insert_date   DATETIME              NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  PRIMARY KEY (attendance_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE UNIQUE INDEX attendance_records_AK1 ON attendance_records(type,active_date);
-- ---------------------------------------------------------------------------------
-- END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION