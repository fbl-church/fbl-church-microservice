-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.2.0.3__Add_VBS_Attendance_Records_Table.sql
-- Author: Sam Butler
-- Date: June 19, 2024
-- Version: V1.2.0.3
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- START
-- ---------------------------------------------------------------------------------

CREATE TABLE vbs_attendance_records (
  attendance_record_id INT          UNSIGNED NOT NULL,
  vbs_theme_id         INT          UNSIGNED NOT NULL,
  money                FLOAT(8,2)            NOT NULL DEFAULT 0.00,
  offering_winners     VARCHAR(50),
  PRIMARY KEY (attendance_record_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE UNIQUE INDEX UX_vbs_attendance_records__attendance_record_id ON vbs_attendance_records(attendance_record_id);

ALTER TABLE vbs_attendance_records ADD CONSTRAINT FK1_vbs_attendance_records__attendance_records
  FOREIGN KEY (attendance_record_id) REFERENCES attendance_records (id) 
    ON DELETE CASCADE 
    ON UPDATE CASCADE;

ALTER TABLE vbs_attendance_records ADD CONSTRAINT FK2_vbs_attendance_records__vbs_themes
  FOREIGN KEY (vbs_theme_id) REFERENCES vbs_themes (id) 
    ON DELETE CASCADE 
    ON UPDATE CASCADE;

-- ---------------------------------------------------------------------------------
-- END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION