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
  attendance_record_id  INT          UNSIGNED NOT NULL,
  child_id              INT          UNSIGNED NOT NULL,
  notes                 LONGTEXT                       DEFAULT NULL,
  updated_user_id       INT          UNSIGNED NOT NULL DEFAULT 0,
  check_in_date         DATETIME              NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  guardian_picked_up_id INT          UNSIGNED          DEFAULT NULL,
  check_out_date        DATETIME                       DEFAULT NULL,
  PRIMARY KEY (attendance_record_id, child_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX IX_children_attendance__child_id ON children_attendance(child_id);
CREATE INDEX IX_children_attendance__updated_user_id ON children_attendance(updated_user_id);

ALTER TABLE children_attendance ADD CONSTRAINT FK1_children_attendance__attendance_records
  FOREIGN KEY (attendance_record_id) REFERENCES attendance_records (id) 
    ON DELETE CASCADE 
    ON UPDATE CASCADE;

ALTER TABLE children_attendance ADD CONSTRAINT FK2_children_attendance__children
  FOREIGN KEY (child_id) REFERENCES children (user_id) 
    ON DELETE CASCADE 
    ON UPDATE CASCADE;

ALTER TABLE children_attendance ADD CONSTRAINT FK3_children_attendance__users
  FOREIGN KEY (updated_user_id) REFERENCES users (id) 
    ON DELETE CASCADE 
    ON UPDATE CASCADE;

ALTER TABLE children_attendance ADD CONSTRAINT FK4_children_attendance__users
  FOREIGN KEY (guardian_picked_up_id) REFERENCES users (id) 
    ON DELETE CASCADE 
    ON UPDATE CASCADE;

-- ---------------------------------------------------------------------------------
-- END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION