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
  attendance_id INT          UNSIGNED NOT NULL,
  user_id       INT          UNSIGNED NOT NULL,
  PRIMARY KEY (attendance_id, user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX attendance_record_workers_IDX1 ON attendance_record_workers(user_id);

ALTER TABLE attendance_record_workers ADD CONSTRAINT attendance_records__attendance_record_workers__FK1 
  FOREIGN KEY (attendance_id) REFERENCES attendance_records (attendance_id) 
    ON DELETE CASCADE 
    ON UPDATE CASCADE;

ALTER TABLE attendance_record_workers ADD CONSTRAINT users__attendance_record_workers__FK2
  FOREIGN KEY (user_id) REFERENCES users (id) 
    ON DELETE CASCADE 
    ON UPDATE CASCADE;
-- ---------------------------------------------------------------------------------
-- END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION