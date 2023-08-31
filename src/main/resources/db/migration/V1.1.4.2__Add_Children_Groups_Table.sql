-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.1.4.2__Add_Children_Groups_Table.sql
-- Author: Sam Butler
-- Date: April 24, 2022
-- Version: V1.1.4.2
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- START
-- ---------------------------------------------------------------------------------

CREATE TABLE children_groups (
  child_id      INT         unsigned NOT NULL,
  church_group  VARCHAR(45)          NOT NULL,
  PRIMARY KEY (child_id,church_group)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX IX_children_groups__church_group ON children_groups(church_group);

ALTER TABLE children_groups ADD CONSTRAINT FK1_children_groups__children
  FOREIGN KEY (child_id) REFERENCES children (user_id) 
    ON DELETE CASCADE 
    ON UPDATE CASCADE;

-- ---------------------------------------------------------------------------------
-- END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION