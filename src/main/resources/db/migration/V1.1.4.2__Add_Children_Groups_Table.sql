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
  church_group  VARCHAR(45)          NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE UNIQUE INDEX children_groups_AK1 ON children_groups(child_id,church_group);
CREATE INDEX children_groups_IDX1 ON children_groups(church_group);

ALTER TABLE children_groups ADD CONSTRAINT children__children_groups__FK1
  FOREIGN KEY (child_id) REFERENCES children (user_id) 
    ON DELETE CASCADE 
    ON UPDATE CASCADE;
-- ---------------------------------------------------------------------------------
-- END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION