-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.2.0.4__Add_VBS_Children_Points_Table.sql
-- Author: Sam Butler
-- Date: June 27, 2024
-- Version: V1.2.0.4
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- START
-- ---------------------------------------------------------------------------------

CREATE TABLE vbs_children_points (
  child_id                 INT          UNSIGNED NOT NULL,
  vbs_attendance_record_id INT          UNSIGNED NOT NULL,
  vbs_point_config_id      INT          UNSIGNED NOT NULL,
  insert_date              DATETIME              NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  PRIMARY KEY (child_id,vbs_attendance_record_id,vbs_point_config_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX IX1_vbs_children_points__vbs_attendance_record_id ON vbs_children_points(vbs_attendance_record_id);
CREATE INDEX IX2_vbs_children_points__vbs_point_config_id ON vbs_children_points(vbs_point_config_id);

ALTER TABLE vbs_children_points ADD CONSTRAINT FK1_vbs_children_points__child_id
  FOREIGN KEY (child_id) REFERENCES children (user_id) 
    ON DELETE CASCADE 
    ON UPDATE CASCADE;

ALTER TABLE vbs_children_points ADD CONSTRAINT FK2_vbs_children_points__vbs_attendance_record_id
  FOREIGN KEY (vbs_attendance_record_id) REFERENCES vbs_attendance_records (attendance_record_id) 
    ON DELETE CASCADE 
    ON UPDATE CASCADE;

ALTER TABLE vbs_children_points ADD CONSTRAINT FK3_vbs_children_points__vbs_point_config_id
  FOREIGN KEY (vbs_point_config_id) REFERENCES vbs_points_config (id) 
    ON DELETE CASCADE 
    ON UPDATE CASCADE;

-- ---------------------------------------------------------------------------------
-- END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION