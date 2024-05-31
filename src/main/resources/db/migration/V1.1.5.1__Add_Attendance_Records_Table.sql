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
  id                 INT          UNSIGNED NOT NULL AUTO_INCREMENT,
  name               VARCHAR(128)          NOT NULL,
  status             VARCHAR(32)           NOT NULL DEFAULT 'PENDING',
  type               VARCHAR(32)           NOT NULL,
  unit_session       VARCHAR(100)                   DEFAULT NULL,
  active_date        DATE                  NOT NULL DEFAULT (CURDATE()),
  closed_date        DATE                           DEFAULT NULL,
  started_by_user_id INT          UNSIGNED          DEFAULT NULL,
  closed_by_user_id  INT          UNSIGNED          DEFAULT NULL,
  insert_date        DATETIME              NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX IX_attendance_records__started_by_user_id ON attendance_records(started_by_user_id);
CREATE INDEX IX_attendance_records__closed_by_user_id ON attendance_records(closed_by_user_id);

ALTER TABLE attendance_records ADD CONSTRAINT FK1_attendance_records__users
  FOREIGN KEY (started_by_user_id) REFERENCES users (id) 
    ON DELETE SET NULL 
    ON UPDATE CASCADE;

ALTER TABLE attendance_records ADD CONSTRAINT FK2_attendance_records__users
  FOREIGN KEY (closed_by_user_id) REFERENCES users (id) 
    ON DELETE SET NULL 
    ON UPDATE CASCADE;

-- ---------------------------------------------------------------------------------
-- END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION