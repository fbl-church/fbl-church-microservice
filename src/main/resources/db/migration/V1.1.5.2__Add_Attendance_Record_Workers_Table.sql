-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.1.5.2__Add_Attendance_Record_Workers_Table.sql
-- Author: Sam Butler
-- Date: April 24, 2022
-- Version: V1.1.5.2
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- START
-- ---------------------------------------------------------------------------------

CREATE TABLE attendance_record_workers (
  attendance_record_id  INT          UNSIGNED NOT NULL,
  user_id               INT          UNSIGNED NOT NULL,
  PRIMARY KEY (attendance_record_id, user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX IX_attendance_record_workers__user_id ON attendance_record_workers(user_id);

ALTER TABLE attendance_record_workers ADD CONSTRAINT FK1_attendance_record_workers__attendance_records
  FOREIGN KEY (attendance_record_id) REFERENCES attendance_records (id) 
    ON DELETE CASCADE 
    ON UPDATE CASCADE;

ALTER TABLE attendance_record_workers ADD CONSTRAINT FK2_attendance_record_workers__users
  FOREIGN KEY (user_id) REFERENCES users (id) 
    ON DELETE CASCADE 
    ON UPDATE CASCADE;

-- ---------------------------------------------------------------------------------
-- END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION