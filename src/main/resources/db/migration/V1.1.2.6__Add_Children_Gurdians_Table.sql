-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.1.2.6__Add_Children_Gurdians_Table.sql
-- Author: Sam Butler
-- Date: April 24, 2022
-- Version: v1.1.2
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- START
-- ---------------------------------------------------------------------------------

CREATE TABLE children_gurdians (
  child_id     INT          UNSIGNED NOT NULL,
  gurdian_id   INT          UNSIGNED NOT NULL,
  relationship VARCHAR(128)          NOT NULL DEFAULT 'OTHER'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE UNIQUE INDEX children_gurdians_AK1 ON children_gurdians(child_id, gurdian_id);

ALTER TABLE children_gurdians ADD CONSTRAINT children__children_gurdians__FK1 
  FOREIGN KEY (child_id) REFERENCES children (id) 
    ON DELETE CASCADE
    ON UPDATE CASCADE;

ALTER TABLE children_gurdians ADD CONSTRAINT gurdians__children_gurdians__FK2
  FOREIGN KEY (gurdian_id) REFERENCES gurdians (user_id) 
    ON DELETE RESTRICT
    ON UPDATE CASCADE;

-- ---------------------------------------------------------------------------------
-- END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION


