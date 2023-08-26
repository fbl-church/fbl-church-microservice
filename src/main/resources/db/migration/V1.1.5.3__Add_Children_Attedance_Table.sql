-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.1.5.3__Add_Children_Attedance_Table.sql
-- Author: Sam Butler
-- Date: April 24, 2022
-- Version: V1.1.5.3
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- START
-- ---------------------------------------------------------------------------------

CREATE TABLE children_attendance (
  attendance_id INT          UNSIGNED NOT NULL,
  user_id       INT          UNSIGNED NOT NULL,
  present       TINYINT(4)   UNSIGNED NOT NULL DEFAULT 0,
  notes         LONGTEXT                       DEFAULT NULL,
  check_in_date DATETIME              NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  PRIMARY KEY (attendance_id, user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX children_attendance_IDX1 ON children_attendance(user_id);

ALTER TABLE children_attendance ADD CONSTRAINT attendance_records__children_attendance__FK1 
  FOREIGN KEY (attendance_id) REFERENCES attendance_records (attendance_id) 
    ON DELETE CASCADE 
    ON UPDATE CASCADE;

ALTER TABLE children_attendance ADD CONSTRAINT users__children_attendance__FK2
  FOREIGN KEY (user_id) REFERENCES users (id) 
    ON DELETE CASCADE 
    ON UPDATE CASCADE;
-- ---------------------------------------------------------------------------------
-- END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION