-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.2.0.1__Add_Started_And_Closed_By_User_Id.sql
-- Author: Sam Butler
-- Date: September 22, 2023
-- Version: V1.2.0.1
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- START
-- ---------------------------------------------------------------------------------

ALTER TABLE attendance_records
ADD COLUMN started_by_user_id INT   UNSIGNED DEFAULT NULL AFTER closed_date,
ADD COLUMN closed_by_user_id  INT   UNSIGNED DEFAULT NULL AFTER started_by_user_id;

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